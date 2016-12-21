lazy val root = (project in file(".")).
  settings(
	name.:=("Cafe"),
	version := "0.1.0",
	organization := "com.capgemini",
	scalaVersion := "2.10.6"
  )
  
  // Add library dependancies for ScalaTest tool
  libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.1"
  libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"
