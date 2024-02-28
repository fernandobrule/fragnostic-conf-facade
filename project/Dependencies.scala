import sbt._

object Dependencies {

  lazy val fragnosticConfBase         = "com.fragnostic"        % "fragnostic-conf-base_2.13"           % "0.1.7"
  lazy val fragnosticConfEnv          = "com.fragnostic"        %  "fragnostic-conf-env_2.13"           % "0.1.10"
  lazy val fragnosticConfCache        = "com.fragnostic"        % "fragnostic-conf-cache_2.13"          % "0.2.8"
  lazy val fragnosticConfProps        = "com.fragnostic"        % "fragnostic-conf-props_2.13"          % "0.1.8"

  lazy val logbackClassic             = "ch.qos.logback"        % "logback-classic"                     % "1.5.0" % "runtime"
  lazy val slf4jApi                   = "org.slf4j"             % "slf4j-api"                           % "2.0.12"
  lazy val scalatestFunSpec           = "org.scalatest"         % "scalatest-funspec_2.13"              % "3.2.18" % Test

}
