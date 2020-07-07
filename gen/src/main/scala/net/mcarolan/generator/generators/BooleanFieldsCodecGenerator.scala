package net.mcarolan.generator.generators

import net.mcarolan.generator.SpecReader.{SpecClass, SpecField, SpecMethod}

import scala.annotation.tailrec

trait CodecGen {
  def encodeForCompLine: String
  def encodeYieldSegment: String
  def decodeForCompLine(last: Option[String]): String
  def decodeYieldSegment: String
}

case class FieldsCodecGenerator(fields: List[SpecField]) {

  @tailrec
  private def buildGenerators(remaining: List[SpecField], acc: List[CodecGen], counter: Int): List[CodecGen] =
    remaining match {
      case head :: tail if isBooleanField(head.resolvedType) =>
        val following = tail.takeWhile(f => isBooleanField(f.resolvedType))
        val afterThat = tail.dropWhile(f => isBooleanField(f.resolvedType))
        buildGenerators(afterThat, acc :+ BooleanFieldsCodecGenerator(counter, List(head) ++ following), counter + 1)
      case head :: tail =>
        buildGenerators(tail, acc :+ SimpleFieldCodecGenerator(head), counter)
      case Nil => acc
    }

  val generators: List[CodecGen] =
    buildGenerators(fields, List.empty, 0)

  val hasBooleanField: Boolean =
    fields.exists(f => scalaFieldType(f.resolvedType) == "AmqpBoolean")

  val lastField: SpecField = fields.last

  val encoderForComp: String =
    generators.map { gen => s"          ${gen.encodeForCompLine}"}.mkString("\n")
  val encoderYield: String =
    generators.map(g => g.encodeYieldSegment).mkString(" ++ ")

  val decoderForComp: String = {
    val withLast =
      List(None) ++ generators.take(generators.length - 1).map(c => Some(c.encodeYieldSegment))

    generators.zip(withLast).map { case (gen, last) =>
      gen.decodeForCompLine(last)
    }.mkString("\n          ")
  }
  def decoderYield(specClass: SpecClass, specMethod: SpecMethod): String = {
    val lastGenerator = generators.last
    s"""DecodeResult(
       |            ${scalaClassName(specClass.name)}.${scalaClassName(specMethod.name)}(${generators.map(_.decodeYieldSegment).mkString(", ")}),
       |            ${lastGenerator.encodeYieldSegment}.remainder
       |          )""".stripMargin
  }
}

case class BooleanFieldsCodecGenerator(booleanGroupNumber: Int, fields: List[SpecField]) extends CodecGen {

  override def encodeForCompLine: String =
    s"booleans${booleanGroupNumber} <- AmqpBoolean.encoder.encode(NonEmptyList.of(${fields.map(f => s"value.${scalaFieldName(f.name)}").mkString(", ")}))"

  override def encodeYieldSegment: String =
    s"booleans${booleanGroupNumber}"

  override def decodeForCompLine(last: Option[String]): String =
    s"""booleans${booleanGroupNumber} <- AmqpBoolean.decoder${fields.size}.decode(${last.map(_ + ".remainder").getOrElse("bv")})
       |          Seq(${fields.map(f => scalaFieldName(f.name)).mkString(", ")}) = booleans${booleanGroupNumber}.value""".stripMargin

  override def decodeYieldSegment: String =
    fields.map(f => scalaFieldName(f.name)).mkString(", ")
}

case class SimpleFieldCodecGenerator(field: SpecField) extends CodecGen {
  override def encodeForCompLine: String =
    s"${scalaFieldName(field.name)} <- ${scalaFieldType(field.resolvedType)}.codec.encode(value.${scalaFieldName(field.name)})"

  override def encodeYieldSegment: String =
    s"${scalaFieldName(field.name)}"

  override def decodeForCompLine(last: Option[String]): String =
    s"${scalaFieldName(field.name)} <- ${scalaFieldType(field.resolvedType)}.codec.decode(${last.map(_ + ".remainder").getOrElse("bv")})"

  override def decodeYieldSegment: String =
    s"${scalaFieldName(field.name)}.value"
}
