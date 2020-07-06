package net.mcarolan.generator

import java.nio.file.{Files, Paths}

import cats.effect.{ExitCode, IO, IOApp}
import net.mcarolan.generator.generators.ClassAndMethodGenerator

import scala.xml.XML

object GenMain extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = {
      IO {
        val elem = XML.loadFile("./gen/src/main/resources/amqp0-9-1.xml")
        val generator = ClassAndMethodGenerator(SpecReader(elem))
        val base = Paths.get("./core/src/main/scala/net/mcarolan/amqpscodec")

        Files.createDirectories(base)
        generator.defineClasses(base)
      }
    }.as(ExitCode.Success)
}
