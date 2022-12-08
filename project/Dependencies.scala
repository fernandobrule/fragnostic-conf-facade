import sbt._

object Dependencies {

  lazy val fragnosticConfBase         = "com.fragnostic"        % "fragnostic-conf-base_2.13"           % "0.1.6"
  lazy val fragnosticConfEnv          = "com.fragnostic"        %  "fragnostic-conf-env_2.13"           % "0.1.10-SNAPSHOT"
  lazy val fragnosticConfCache        = "com.fragnostic"        % "fragnostic-conf-cache_2.13"          % "0.2.8-SNAPSHOT"
  lazy val fragnosticConfProps        = "com.fragnostic"        % "fragnostic-conf-props_2.13"          % "0.1.8-SNAPSHOT"

  lazy val logbackClassic             = "ch.qos.logback"        % "logback-classic"                     % "1.3.0-alpha12" % "runtime"
  lazy val slf4jApi                   = "org.slf4j"             % "slf4j-api"                           % "2.0.0-alpha5"
  lazy val scalatestFunSpec           = "org.scalatest"        %% "scalatest-funspec"                   % "3.3.0-SNAP3" % Test

}
