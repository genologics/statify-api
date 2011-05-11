package com.genologics.statsapi.resources

import akka.actor.Actor._
import javax.ws.rs.core.Response
import scala.collection.JavaConversions._
import java.util.UUID
import javax.ws.rs._
import org.codehaus.jackson.map.ObjectMapper
import com.genologics.statsapi.services.{KeyAgreementService, KeyAgreementRequest, RegistrationRequest, RegistrationService}

/**
 * RootResource.
 *
 * @author Cameron Fieber <cameron.fieber@genologics.com>
 */
@Path("/")
class RootResource {
    val G = BigInt("60370961825278825174636775293064291624972079197512439435765122590268013820288271884219976882234713118936927504432577483244531152816411592214546757203954451361933139898716312348867321825572951447166645449277815716810000902605114867463648163326447214251553674608114091200577007286238216359828510222685081375044")
    val P = BigInt("96670533407643149796392289231323231466810860032356119858878255753217478033447857463117744774871963293202751783087362831792660162196918161525356816239821376446877626138884380414191524833177407882177829791232706993108696987025715201274222524862134823510126263964052033009603706019611938498141169902462041529849")
    val L = 1024

    @GET
    @Path("/register")
    @Produces(Array("application/json"))
    def registrationParams() = {
        Response.ok(new ObjectMapper().writeValueAsString(asJavaMap(Map("algorithm" -> "Diffie-Hellman", "G" -> G.toString(), "P" -> P.toString(), "L" -> L.toString())))).build
    }

    @POST
    @Path("/register")
    @Produces(Array("application/json"))
    def register(@QueryParam("pub") pub:String) = {

        val uuid = UUID.randomUUID.toString

        val result = (for {
            keyService <- registry.actorFor[KeyAgreementService]
            secret <- (keyService !! KeyAgreementRequest(P, G, L, pub)).as[(String, String)]
            registrationService <- registry.actorFor[RegistrationService]
            registration <- (registrationService !! RegistrationRequest(uuid, secret._2)).as[Long]
        } yield secret) match {
            case Some((publicKey : String, secret: String)) => (publicKey, secret)
            case _ => throw new Exception("failed")
        }

        Response.ok(new ObjectMapper().writeValueAsString(asJavaMap(Map("uuid" -> uuid, "public-key" -> result._1)))).build
    }
}