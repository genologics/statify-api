import sbt._

class StatsApiProject(info: ProjectInfo) extends DefaultProject(info) with IdeaProject with AkkaProject {
    val akkaHttp = akkaModule("http")
}