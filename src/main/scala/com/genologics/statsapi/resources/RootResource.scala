package com.genologics.statsapi.resources

import javax.ws.rs.{Produces, GET, Path}
import com.genologics.statsapi.services.RegistrationService
import akka.actor.Actor._
import javax.xml.bind.annotation.XmlRootElement

/**
 * RootResource.
 *
 * @author Cameron Fieber <cameron.fieber@genologics.com>
 */

@XmlRootElement
case class Response(var success: Boolean, var result: String) {
    def this() = this(false, "")
}

@Path("/")
class RootResource {


    @GET
    @Path("/register")
    @Produces(Array("text/plain"))
    def register = {
        println("register")

        val a = registry.actorFor[RegistrationService]
        println("Actor: " + a)
        val result = (a.get !! "Register").as[String]
        println("Result: " + result)
//        val result = for {a <- registry.actorFor[RegistrationService]
//                          r <- (a !! "Register").as[String]} yield r
        result match {
            case Some(uuid) => uuid
            case _ => "unable to register"
        }
    }
}

