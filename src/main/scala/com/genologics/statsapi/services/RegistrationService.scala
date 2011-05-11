package com.genologics.statsapi.services

import akka.actor.Actor
import com.genologics.statsapi.data.{StatsSchema, Registration}
import org.squeryl._
import org.squeryl.adapters.MySQLAdapter
import org.squeryl.PrimitiveTypeMode._

/**
 * RegistrationService.
 *
 * @author Cameron Fieber <cameron.fieber@genologics.com>
 */

case class RegistrationRequest(uuid : String, token : String)

class RegistrationService extends Actor {
    def receive = {
        case r : RegistrationRequest => {
            Class.forName("com.mysql.jdbc.Driver")
            SessionFactory.concreteFactory = Some(() =>
                    Session.create(
                        java.sql.DriverManager.getConnection("jdbc:mysql://localhost/statifyDB", "statify", "statify"),
                        new MySQLAdapter))

            val reg : Registration = inTransaction {
                StatsSchema.registrations.insert(new Registration(r.uuid, r.token))
            }

            self.reply(reg.id)
        }
    }
}