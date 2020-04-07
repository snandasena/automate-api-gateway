package com.lagom.impl

import com.lagom.api.IntegrationService
import play.api.libs.json.JsObject

import scala.concurrent.Future

class IntegrationServiceImpl extends IntegrationService {
  override def internalCall(req: JsObject): Future[JsObject] = {
    Future.successful {
      println(req)
      req
    }
  }
}
