package net.mcarolan.generator


import java.nio.file.{Files, Path, Paths}

import scala.jdk.javaapi.CollectionConverters
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

trait Formatting {
  def upperFirstLetter(s: String): String =
    s.take(1).toUpperCase + s.drop(1)

  def scalaClassName(s: String): String =
    s.split("-").map(upperFirstLetter).mkString("")

  def scalaFieldName(s: String): String = {
    val parts = s.split("-")
    val result =
      parts.take(1).mkString("") + parts.drop(1).map(upperFirstLetter).mkString("")

    if (result == "type")
      s"`$result`"
    else
      result
  }
}

case class SpecField(name: String, resolvedType: String)
case class SpecMethod(index: Int, name: String, fields: List[SpecField])
case class SpecClass(index: Int, name: String, methods: List[SpecMethod])

case class SpecReader(root: Elem) {
  private def attributeValue(elem: Node, key: String): String =
    elem.attribute(key).map(_.head.text).getOrElse {
      throw new RuntimeException(s"$key not defined at $elem")
    }

  lazy val domainTypes: Map[String, String] = (root \\ "domain").map { node =>
    attributeValue(node, "name") -> attributeValue(node, "type")
  }.toMap

  private def fieldFromNode(elem: Node): SpecField = {
    val domainType = optionalAttributeValue(elem, "domain")
    val `type` = optionalAttributeValue(elem, "type")
    val resolvedType =
      `type`.getOrElse {
        domainTypes.getOrElse(domainType.get, throw new RuntimeException(s"No domain type ${domainType} defined"))
      }
    SpecField(
      attributeValue(elem, "name"),
      resolvedType
    )
  }

  private def methodFromNode(elem: Node): SpecMethod =
    SpecMethod(
      attributeValue(elem, "index").toInt,
      attributeValue(elem, "name"),
      (elem \\ "field").map(fieldFromNode).toList
    )

  private def classFromNode(elem: Node): SpecClass = {
    val className = attributeValue(elem, "name")
    SpecClass(
      attributeValue(elem, "index").toInt,
      className,
      (elem \\ "method").map(methodFromNode).toList
    )
  }

  lazy val classes: List[SpecClass] = (root \\ "class").map(classFromNode).toList

}

case class Generator(specReader: SpecReader) extends Formatting {

  private val basePackage = "net.mcarolan.amqpscodec"

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

  def defineField(specField: SpecField): String = {
    s"      ${scalaFieldName(specField.name)}: ${fieldType(specField.resolvedType)}"
  }

  def defineMethod(specMethod: SpecMethod): String = {
    s"""
       |    case class ${scalaClassName(specMethod.name)}(
       |${specMethod.fields.map(defineField).mkString(",\n")}
       |    ) extends Method
       |    object ${scalaClassName(specMethod.name)} {
       |      val index: AmqpShort = AmqpShort(${specMethod.index})
       |    }
       |""".stripMargin
  }

  def defineClass(specClass: SpecClass): String = {
    s"""
       |  case object ${scalaClassName(specClass.name)} {
       |    val index: AmqpShort = AmqpShort(${specClass.index})
       |    sealed trait Method extends AmqpMethod
       |    ${specClass.methods.map(defineMethod).mkString("")}
       |  }
       |""".stripMargin
  }

  def defineClasses: String =
      s"""package ${basePackage}
         |
         |import ${basePackage}.AmqpTypes._
         |
         |package object spec {
         |
         |  sealed trait AmqpMethod
         |  ${specReader.classes.map(defineClass).mkString("")}
         |}
         |""".stripMargin
}
