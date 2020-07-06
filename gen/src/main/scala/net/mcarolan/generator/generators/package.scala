package net.mcarolan.generator

import java.nio.file.{Files, Path}

import scala.jdk.javaapi.CollectionConverters

package object generators {

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

  def scalaFieldType(resolvedType: String): String =
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
    ).getOrElse(resolvedType, throw new RuntimeException(s"No type for ${resolvedType}"))

  val basePackage = "net.mcarolan.amqpscodec"

  def write(path: Path, s: String): Unit =
    Files.write(path, CollectionConverters.asJava(s.split("\n")))

}
