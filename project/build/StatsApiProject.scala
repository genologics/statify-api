import sbt._

class StatsApiProject(info: ProjectInfo) extends DefaultWebProject(info) with IdeaProject with AkkaProject {

    val squeryl = "org.squeryl" % "squeryl_2.8.1" % "0.9.4-RC6"
    val mysql = "mysql" % "mysql-connector-java" % "5.1.16"

    val servletApi = "javax.servlet" % "servlet-api" % "2.5" % "provided"

    val jettyServer = "org.eclipse.jetty"  % "jetty-server"   % "7.0.1.v20091125" % "test"
    val jettyWebapp = "org.eclipse.jetty"  % "jetty-webapp"   % "7.0.1.v20091125" % "test"

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
