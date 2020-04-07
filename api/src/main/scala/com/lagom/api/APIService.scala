package com.lagom.api

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.transport.Method
import com.lightbend.lagom.scaladsl.api.{Descriptor, Service, ServiceCall}
import play.api.libs.json._

trait APIService extends Service {

  private final val root = "/api/"
  private final val urlRegex = "/api/$id<[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]+>"

  def ping(): ServiceCall[NotUsed, String]

  def transform(): ServiceCall[JsObject, JsObject]

  override def descriptor: Descriptor = {
    import Service._

    named(name = "api-service")
      .withCalls(
        restCall(Method.GET, pathPattern = urlRegex, transform _),
        restCall(Method.GET, pathPattern = root, ping _),
        restCall(Method.POST, pathPattern = urlRegex, transform _),
        restCall(Method.POST, pathPattern = root, ping _)
      ).withAutoAcl(true)

  }

}
