package com.lagom.api

import play.api.libs.json.JsObject

import scala.concurrent.Future

trait IntegrationService {
  def internalCall(req: JsObject): Future[JsObject]
}
