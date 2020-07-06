import sbt._

object Dependencies {
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.1.1"
  lazy val catsEffect = "org.typelevel" %% "cats-effect" % "2.1.3"
  lazy val xml = "org.scala-lang.modules" %% "scala-xml" % "2.0.0-M1"
  lazy val scodecCore = "org.scodec" %% "scodec-core" % "1.11.7"
}
