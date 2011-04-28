package com.genologics.statsapi

import javax.servlet.{ServletContextListener, ServletContextEvent}
import akka.servlet.AkkaLoader
import akka.actor.BootableActorLoaderService

/**
 * AkkaInitializer.
 *
 * @author Cameron Fieber <cameron.fieber@genologics.com>
 */
class AkkaInitializer extends ServletContextListener {
    lazy val loader = new AkkaLoader

    def contextDestroyed(e: ServletContextEvent) {
        loader.shutdown
    }

    def contextInitialized(e: ServletContextEvent) {
        loader.boot(true, new BootableActorLoaderService {})
    }
}