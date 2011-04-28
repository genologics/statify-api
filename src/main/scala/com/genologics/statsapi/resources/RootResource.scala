package com.genologics.statsapi.resources

import javax.ws.rs.{Produces, GET, Path}
import com.genologics.statsapi.services.RegistrationService
import akka.actor.Actor._
import javax.ws.rs.core.Response

/**
 * RootResource.
 *
 * @author Cameron Fieber <cameron.fieber@genologics.com>
 */
@Path("/")
class RootResource {

    @GET
    @Path("/register")
    @Produces(Array("text/plain"))
    def register = {
        val result = for {
            a <- registry.actorFor[RegistrationService]
            r <- (a !! "Register").as[String]
        } yield r
        result match {
            case Some(uuid) => Response.ok(uuid).build
            case _ => Response.serverError().entity("unable to register").build
        }
    }
}

