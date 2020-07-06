package net.mcarolan.generator

import scala.xml.{Elem, Node}

/*
TODO:
    object Consume {
      val index: AmqpShort = AmqpShort(20)
      val encoder: Encoder[Consume] = Encoder[Consume] { consume: Consume =>
        for {
          reserved1 <- AmqpShort.codec.encode(consume.reserved1)
          queue <- ShortString.codec.encode(consume.queue)
          consumerTag <- ShortString.codec.encode(consume.consumerTag)
          booleans1 <- MethodArgumentCodec.booleansEncoder.encode(NonEmptyList.of(consume.noLocal.value, consume.noAck.value, consume.exclusive.value, consume.noWait.value))
          arguments <- Table.codec.encode(consume.arguments)
        } yield reserved1 ++ queue ++ consumerTag ++ booleans1 ++ arguments
      }
      val decoder: Decoder[Consume] = Decoder[Consume] { bv : BitVector =>
        for {
          reserved1 <- AmqpShort.codec.decode(bv)
          queue <- ShortString.codec.decode(reserved1.remainder)
          consumerTag <- ShortString.codec.decode(queue.remainder)
          booleans1 <- MethodArgumentCodec.booleansDecoder(4).map(_.map(AmqpBoolean)).decode(consumerTag.remainder)
          Seq(noLocal, noAck, exclusive, noWait) = booleans1.value
          arguments <- Table.codec.decode(booleans1.remainder)
        } yield DecodeResult(
          Consume(reserved1.value, queue.value, consumerTag.value, noLocal, noAck, exclusive, noWait, arguments.value),
          arguments.remainder
        )
      }
      val codec: Codec[Consume] = Codec(encoder, decoder)
    }
 */

case class Generator(root: Elem) {

  lazy val domainTypes: Map[String, String] = (root \\ "domain").map { node =>
    attributeValue(node, "name") -> attributeValue(node, "type")
  }.toMap

  def upperFirstLetter(string: String): String =
    string.take(1).toUpperCase + string.drop(1)

  def formatAsClassName(string: String): String =
    string.split("-").map(upperFirstLetter).mkString("")

  def formatAsFieldName(string: String): String = {
    val parts = string.split("-")
    val result =
      parts.take(1).mkString("") + parts.drop(1).map(upperFirstLetter).mkString("")

    if (result == "type")
      s"`$result`"
    else
      result
  }

  def fieldType(string: String): String =
    Map[String, String](
      "octet" -> "AmqpByte",
      "longstr" -> "LongString",
      "shortstr" -> "ShortString",
      "short" -> "AmqpShort",
      "long" -> "AmqpLong",
      "path" -> "ShortString",
      "bit" -> "AmqpBoolean",
      "table" -> "Table",
      "longlong" -> "AmqpLongLong"
    ).getOrElse(string, throw new RuntimeException(s"No type for ${string}"))

  def attributeValue(elem: Node, key: String): String =
    elem.attribute(key).map(_.head.text).getOrElse {
      throw new RuntimeException(s"$key not defined at $elem")
    }

  def optionalAttributeValue(elem: Node, key: String): Option[String] =
    elem.attribute(key).flatMap(_.headOption.map(_.text))

  def defineFieldType(elem: Node): String = {
    optionalAttributeValue(elem, "domain").map { domainType =>
      domainTypes.getOrElse(domainType, throw new RuntimeException(s"No domain type ${domainType} defined"))
    }
      .orElse(optionalAttributeValue(elem, "type"))
      .map(fieldType)
      .getOrElse(s"cannot find a type attribute for ${elem}")
  }

  def fieldName(elem: Node): String =
    formatAsFieldName(attributeValue(elem, "name"))

  def defineField(elem: Node): String =
    s"      ${fieldName(elem)}: ${defineFieldType(elem)}"

  def defineMethod(className: String)(elem: Node): String = {
    val methodScalaName: String =
      formatAsClassName(attributeValue(elem, "name"))

    val fields = elem \\ "field"

    s"""
       |    case class ${methodScalaName}(
       |${fields.map(defineField).mkString(",\n")}
       |    ) extends Method {
       |      override val classIndex: AmqpShort = ${className}.index
       |      override val methodIndex: AmqpShort = ${methodScalaName}.index
       |      override val argumentValues: List[AmqpType] = List(${fields.map(fieldName).mkString(", ")})
       |    }
       |    object ${methodScalaName} {
       |      val index: AmqpShort = AmqpShort(${attributeValue(elem, "index")})
       |    }
       |""".stripMargin
  }

  def defineClass(elem: Node): String = {
    val classScalaName: String = upperFirstLetter(attributeValue(elem, "name"))
    s"""
       |  case object ${classScalaName} {
       |    val index: AmqpShort = AmqpShort(${attributeValue(elem, "index")})
       |    sealed trait Method extends AmqpClasses.Method
       |    ${(elem \\ "method").map(defineMethod(classScalaName)).mkString("")}
       |  }
       |""".stripMargin
  }

  def defineClasses: String = {
    s"""
       |object AmqpClasses {
       |
       |  sealed trait Method {
       |    val classIndex: AmqpShort
       |    val methodIndex: AmqpShort
       |    val argumentValues: List[AmqpType]
       |  }
       |  ${(root \\ "class").map(defineClass).mkString("")}
       |}
       |""".stripMargin
  }
}
