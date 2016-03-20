name := """slick-grid-example"""

version := "1.0-SNAPSHOT"

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0-M5" cross CrossVersion.full)

val gridzzly = project

lazy val root = (project in file(".")).enablePlugins(PlayScala).dependsOn(gridzzly).aggregate(gridzzly)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  cache,
  ws,
  specs2 % Test
)

libraryDependencies ++= Seq(
  "com.github.nscala-time" %% "nscala-time" % "2.2.0",
  "com.github.tototoshi" %% "slick-joda-mapper" % "2.0.0",
  "com.typesafe.play" %% "play-slick" % "1.0.1",
  "com.h2database" % "h2" % "1.4.187",
  "com.typesafe.play" %% "play-slick-evolutions" % "1.0.1",
  "org.scalaz" %% "scalaz-core" % "7.1.3",
  "it.justwrote" %% "scala-faker" % "0.3",
  "org.scalatest" %% "scalatest" % "2.2.1" % "test",
  "org.scalatestplus" %% "play" % "1.4.0-M3" % "test"
)



resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

resolvers += "justwrote" at "http://repo.justwrote.it/releases/"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator
