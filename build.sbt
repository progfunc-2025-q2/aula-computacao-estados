val scala3Version = "3.7.0"

lazy val root = project
  .in(file("."))
  .settings(
    name := "student-template",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-core" % "2.10.0",
      "org.scalameta" %% "munit" % "1.0.0" % Test
    )
  )
