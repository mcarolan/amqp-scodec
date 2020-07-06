package net.mcarolan.generator

import java.io.{BufferedWriter, FileWriter}
import java.nio.file.{Files, Paths}

import cats.effect.{ExitCode, IO, IOApp, Resource}

import scala.jdk.javaapi.CollectionConverters
import scala.xml.XML

object GenMain extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = {
      IO {
        val elem = XML.loadFile("./gen/src/main/resources/amqp0-9-1.xml")
        val generator = Generator(SpecReader(elem))
        val base = Paths.get("./core/src/main/scala/net/mcarolan/amqpscodec/spec")
        val source = generator.defineClasses

        Files.createDirectories(base)
        Files.write(base.resolve("package.scala"), CollectionConverters.asJava(source.split("\n")))
      }
    }.as(ExitCode.Success)
}
