name := "simple-ag-server"

version := "0.1.0"

scalaVersion := "2.13.3"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http" % "10.2.9",
  "com.typesafe.akka" %% "akka-actor-typed" % "2.6.19",
  "com.typesafe.akka" %% "akka-stream" % "2.6.19"
)

enablePlugins(JavaAppPackaging)

// Configure sbt-native-packager
dockerBaseImage := "openjdk:11-jre-slim"
maintainer in Docker := "Robert Gomes (robert.gomes@agoda.com)"
dockerExposedPorts := Seq(8080)
mainClass in Compile := Some("SimpleServer")