package com.genologics.statsapi

import akka.actor._
import akka.actor.Actor._
import akka.config.Supervision._
import services.{KeyAgreementService, RegistrationService}

/**
 * Akka Boot configuration.
 *
 * @author Cameron Fieber <cameron.fieber@genologics.com>
 */
class Boot {
    val factory = SupervisorFactory(
        SupervisorConfig(
            OneForOneStrategy(List(classOf[Exception]), 3, 100),
            Supervise(
                actorOf[RegistrationService],
                Permanent) ::
            Supervise(
                actorOf[KeyAgreementService],
                Permanent) ::
            Nil
        ))

    val supervisor = factory.newInstance
    supervisor.start
}