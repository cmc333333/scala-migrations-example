import sbt._

class Project(info: ProjectInfo) extends DefaultProject(info) {
  val lift_postgresql = "postgresql" % "postgresql" % "8.4-701.jdbc4"
  val slf4j = "org.slf4j" % "slf4j-log4j12" % "1.6.4"
}
