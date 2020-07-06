package net.mcarolan.generator.generators

import java.nio.file.{Files, Path}

import net.mcarolan.generator.SpecReader
import net.mcarolan.generator.SpecReader.{SpecClass, SpecMethod}

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
case class CodecGenerator(specReader: SpecReader) {

  private def forCompEntries(specMethod: SpecMethod): String =
    specMethod.fields.map { field =>
      s"          ${scalaFieldName(field.name)} <- ${scalaFieldType(field.resolvedType)}.codec.encode(value.${scalaFieldName(field.name)})"
    }.mkString("\n")

  private def yieldEntries(specMethod: SpecMethod): String =
    specMethod.fields.map(f => scalaFieldName(f.name)).mkString(" ++ ")

  private def defineForMethod(base: Path, specClass: SpecClass)(specMethod: SpecMethod): Unit = {
    val file = base.resolve(s"${scalaClassName(specMethod.name)}.scala")
    write(file,
      s"""package ${basePackage}.codecs.${scalaPackageName(specClass.name)}
         |
         |import scodec.Encoder
         |import ${basePackage}.spec.${scalaClassName(specClass.name)}.${scalaClassName(specMethod.name)}
         |import ${basePackage}.AmqpTypes._
         |
         |object ${scalaClassName(specMethod.name)} {
         |    val ${scalaClassName(specClass.name)}${scalaClassName(specMethod.name)}Encoder: Encoder[${scalaClassName(specMethod.name)}] =
         |      Encoder[${scalaClassName(specMethod.name)}] { value: ${scalaClassName(specMethod.name)} =>
         |        for {
         |${forCompEntries(specMethod)}
         |        } yield ${yieldEntries(specMethod)}
         |      }
         |}
         |""".stripMargin)
  }

  private def classPackageObjectSource(specClass: SpecClass): String =
    s"""
       |package ${basePackage}.codecs
       |
       |package object ${scalaPackageName(specClass.name)} {
       |${specClass.methods.map(m => s"   import ${scalaClassName(m.name)}._").mkString("\n")}
       |}
       |""".stripMargin

  private def defineForClass(base: Path)(specClass: SpecClass): Unit = {
    val classDir = base.resolve(scalaPackageName(specClass.name))
    Files.createDirectories(classDir)

    specClass.methods.foreach(defineForMethod(classDir, specClass))
    write(classDir.resolve("package.scala"), classPackageObjectSource(specClass))
  }

  def defineCodecs(root: Path): Unit = {
    val codecsDir = root.resolve("codecs")
    Files.createDirectories(codecsDir)

    specReader.classes.foreach(defineForClass(codecsDir))
  }

}
