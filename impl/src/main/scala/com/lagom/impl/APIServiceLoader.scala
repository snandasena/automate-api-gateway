package com.lagom.impl

import com.lagom.api.{APIService, IntegrationService}
import com.lightbend.lagom.scaladsl.api.ServiceLocator
import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import com.lightbend.lagom.scaladsl.server.{LagomApplication, LagomApplicationContext, LagomApplicationLoader, LagomServer}
import com.softwaremill.macwire._
import play.api.libs.ws.ahc.AhcWSComponents
import play.api.mvc.EssentialFilter

class APIServiceLoader extends LagomApplicationLoader {
  override def load(context: LagomApplicationContext): LagomApplication = new APIApplication(context) {
    override def serviceLocator: ServiceLocator = NoServiceLocator
  }

  override def loadDevMode(context: LagomApplicationContext): LagomApplication =
    new APIApplication(context) with LagomDevModeComponents
}


abstract class APIApplication(context: LagomApplicationContext)
  extends LagomApplication(context)
    with AhcWSComponents {

  override lazy val lagomServer: LagomServer = serverFor[APIService](wire[APIServiceImpl])

  lazy val integrationService:IntegrationService = wire[IntegrationServiceImpl]

  lazy val apiFilter = new APIFilter(integrationService)
  override val httpFilters: Seq[EssentialFilter] = Seq(apiFilter)
}