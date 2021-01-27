import sbt._
import Keys._

object Dependencies {
  
  lazy val logbackClassic = "ch.qos.logback" % "logback-classic" % "1.2.3" % "runtime"
  lazy val slf4jApi = "org.slf4j" % "slf4j-api" % "1.7.25"
  lazy val scalatest = "org.scalatest" %% "scalatest" % "3.0.8" % "test"

  lazy val fragnosticConfBase = "com.fragnostic" % "fragnostic-conf-base_2.13" % "0.1.3"
  lazy val fragnosticConfCache = "com.fragnostic" % "fragnostic-conf-cache_2.13" % "0.1.1"
  lazy val fragnosticConfEnv = "com.fragnostic" % "fragnostic-conf-env_2.13" % "0.1.1"
  lazy val fragnosticConfProps = "com.fragnostic" % "fragnostic-conf-props_2.13" % "0.1.1"
  lazy val fragnosticConfDb = "com.fragnostic" % "fragnostic-conf-db_2.13" % "0.1.1"

}
