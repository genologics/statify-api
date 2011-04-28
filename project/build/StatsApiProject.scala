import sbt._

class StatsApiProject(info: ProjectInfo) extends DefaultWebProject(info) with IdeaProject with AkkaProject {

    val servletApi = "javax.servlet" % "servlet-api" % "2.5" % "provided"

    val jettyServer = "org.eclipse.jetty"  % "jetty-server"   % "7.0.1.v20091125" % "test"
    val jettyWebapp = "org.eclipse.jetty"  % "jetty-webapp"   % "7.0.1.v20091125" % "test"
//    val jetty7 = "org.mortbay.jetty" % "jetty" % "6.1.14" % "test"

    override def ivyXML =
        <dependencies>
            <dependency org="se.scalablesolutions.akka" name="akka-http" rev="1.0">
                <exclude module="jetty-server"/>
                <exclude module="jetty-util"/>
                <exclude module="jetty-servlet"/>
                <exclude module="jetty-xml"/>
            </dependency>
        </dependencies>
}
