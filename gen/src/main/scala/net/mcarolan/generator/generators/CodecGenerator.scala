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

  private def sourceForNoArgMethod(specClass: SpecClass, specMethod: SpecMethod): String =
    s"""package ${basePackage}.codecs.${scalaPackageName(specClass.name)}
       |
       |import scodec.{Attempt, DecodeResult, Decoder, Encoder}
       |import ${basePackage}.spec.${scalaClassName(specClass.name)}
       |import scodec.bits.BitVector
       |
       |object ${scalaClassName(specMethod.name)} {
       |    val ${scalaClassName(specClass.name)}${scalaClassName(specMethod.name)}Encoder: Encoder[${scalaClassName(specClass.name)}.${scalaClassName(specMethod.name)}] =
       |      Encoder[${scalaClassName(specClass.name)}.${scalaClassName(specMethod.name)}] { _: ${scalaClassName(specClass.name)}.${scalaClassName(specMethod.name)} => Attempt.successful(BitVector.empty) }
       |    val ${scalaClassName(specClass.name)}${scalaClassName(specMethod.name)}Decoder: Decoder[${scalaClassName(specClass.name)}.${scalaClassName(specMethod.name)}] =
       |      Decoder[${scalaClassName(specClass.name)}.${scalaClassName(specMethod.name)}] { bv: BitVector => Attempt.Successful(DecodeResult(${scalaClassName(specClass.name)}.${scalaClassName(specMethod.name)}(), bv)) }
       |}
       |""".stripMargin

  private def sourceForMethod(specClass: SpecClass, specMethod: SpecMethod): String = {
    val generator = FieldsCodecGenerator(specMethod.fields)

    s"""package ${basePackage}.codecs.${scalaPackageName(specClass.name)}
       |
       |import scodec.{Encoder, Decoder, DecodeResult}
       |import ${basePackage}.spec.${scalaClassName(specClass.name)}
       |import ${basePackage}.AmqpTypes._
       |import scodec.bits.BitVector
       |${if (generator.hasBooleanField) "import cats.data.NonEmptyList\n" else "" }
       |object ${scalaClassName(specMethod.name)} {
       |    val ${scalaClassName(specClass.name)}${scalaClassName(specMethod.name)}Encoder: Encoder[${scalaClassName(specClass.name)}.${scalaClassName(specMethod.name)}] =
       |      Encoder[${scalaClassName(specClass.name)}.${scalaClassName(specMethod.name)}] { value: ${scalaClassName(specClass.name)}.${scalaClassName(specMethod.name)} =>
       |        for {
       |${generator.encoderForComp}
       |        } yield ${generator.encoderYield}
       |      }
       |
       |    val ${scalaClassName(specClass.name)}${scalaClassName(specMethod.name)}Decoder: Decoder[${scalaClassName(specClass.name)}.${scalaClassName(specMethod.name)}] =
       |      Decoder[${scalaClassName(specClass.name)}.${scalaClassName(specMethod.name)}] { bv : BitVector =>
       |        for {
       |          ${generator.decoderForComp}
       |        } yield ${generator.decoderYield(specClass, specMethod)}
       |      }
       |}
       |""".stripMargin
  }

  private def defineForMethod(base: Path, specClass: SpecClass)(specMethod: SpecMethod): Unit = {
    val file = base.resolve(s"${scalaClassName(specMethod.name)}.scala")
    val source =
      if (specMethod.fields.isEmpty)
        sourceForNoArgMethod(specClass, specMethod)
      else
        sourceForMethod(specClass, specMethod)

    write(file, source)
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
