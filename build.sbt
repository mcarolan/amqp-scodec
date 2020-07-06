import Dependencies._

ThisBuild / scalaVersion     := "2.13.2"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "net.mcarolan"
ThisBuild / organizationName := "amqp-scodec"

val commonSettings = Seq(
  libraryDependencies ++= Seq(
    scalaTest % "test",
    catsEffect
  )
)

lazy val core = (project in file("core"))
  .settings(commonSettings)
  .settings(
    libraryDependencies ++= Seq(
      scodecCore
    )
  )

lazy val gen = (project in file("gen"))
  .settings(commonSettings)
  .settings(
    libraryDependencies += xml
  )

lazy val root = (project in file("."))
  .settings(
    name := "amqp-scodec",
    libraryDependencies += scalaTest % Test
  )
  .aggregate(core, gen)

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
