package net.mcarolan.generator.generators

import java.nio.file.{Files, Path}

import net.mcarolan.generator.SpecReader._
import net.mcarolan.generator.SpecReader

case class ClassAndMethodGenerator(specReader: SpecReader) {

  private def defineField(specField: SpecField): String = {
    s"      ${scalaFieldName(specField.name)}: ${scalaFieldType(specField.resolvedType)}"
  }

  private def defineMethod(specMethod: SpecMethod): String = {
    s"""
       |    case class ${scalaClassName(specMethod.name)}(
       |${specMethod.fields.map(defineField).mkString(",\n")}
       |    ) extends Method
       |    object ${scalaClassName(specMethod.name)} {
       |      val index: AmqpShort = AmqpShort(${specMethod.index})
       |    }
       |""".stripMargin
  }

  private def defineClass(specClass: SpecClass): String = {
    s"""
       |  case object ${scalaClassName(specClass.name)} {
       |    val index: AmqpShort = AmqpShort(${specClass.index})
       |    sealed trait Method extends AmqpMethod
       |    ${specClass.methods.map(defineMethod).mkString("")}
       |  }
       |""".stripMargin
  }

  def defineClasses(root: Path): Unit = {
    val source =
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

    val specDir = root.resolve("spec")
    Files.createDirectories(specDir)
    write(specDir.resolve("package.scala"), source)
  }
}
