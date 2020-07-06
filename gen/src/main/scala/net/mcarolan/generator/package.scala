package net.mcarolan

import scala.xml.Node

package object generator {

  def attributeValue(elem: Node, key: String): String =
    elem.attribute(key).map(_.head.text).getOrElse {
      throw new RuntimeException(s"$key not defined at $elem")
    }

  def optionalAttributeValue(elem: Node, key: String): Option[String] =
    elem.attribute(key).flatMap(_.headOption.map(_.text))

}
