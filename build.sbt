name := "futuresComposition"

version := "0.0.1"

scalaVersion := "2.11.0"

javacOptions ++= Seq("-source", "1.8", "-target", "1.8")

scalacOptions += "-target:jvm-1.7"

libraryDependencies ++= Seq(
  "io.reactivex" % "rxjava" % "1.0.0-rc.3",
  "org.scala-lang.modules" %% "scala-async" % "0.9.2")
