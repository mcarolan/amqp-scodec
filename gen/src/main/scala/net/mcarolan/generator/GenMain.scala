package net.mcarolan.generator

import java.nio.file.{Files, Paths}

import cats.effect.{ExitCode, IO, IOApp}
import net.mcarolan.generator.generators.{ClassAndMethodGenerator, CodecGenerator}

import scala.xml.XML

object GenMain extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = {
      IO {
        val elem = XML.loadFile("./gen/src/main/resources/amqp0-9-1.xml")
        val base = Paths.get("./core/src/main/scala/net/mcarolan/amqpscodec")

        val specReader = SpecReader(elem)

        Files.createDirectories(base)
        ClassAndMethodGenerator(specReader).defineClasses(base)
        CodecGenerator(specReader).defineCodecs(base)
      }
    }.as(ExitCode.Success)
}
