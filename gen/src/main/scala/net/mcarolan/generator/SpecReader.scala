package net.mcarolan.generator

import scala.xml.{Elem, Node}

object SpecReader {
  case class SpecField(name: String, resolvedType: String)
  case class SpecMethod(index: Int, name: String, fields: List[SpecField])
  case class SpecClass(index: Int, name: String, methods: List[SpecMethod])
}

case class SpecReader(root: Elem) {
  import SpecReader._

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
