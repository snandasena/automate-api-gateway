import sbt.Keys.libraryDependencies

name := "lagom-sample"

version := "0.1"

scalaVersion := "2.12.8"

val macwire = "com.softwaremill.macwire" %% "macros" % "2.3.0" % "provided"


lazy val `lagom-sample` = (project in file("."))
  .aggregate(
    `api`,
    `impl`)


lazy val `api` = (project in file("api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )

lazy val `impl` = (project in file("impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslClient,
      macwire
    )
  ).dependsOn(
  `api`
)

lagomCassandraEnabled in ThisBuild := false

lagomKafkaEnabled in ThisBuild := false