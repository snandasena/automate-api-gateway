package com.lagom.impl

import com.lagom.api.APIService
import com.lightbend.lagom.scaladsl.api.ServiceCall

import scala.concurrent.Future

class APIServiceImpl extends APIService {
  override def ping() = ServiceCall(_ => {
    Future.successful("Server is running....")
  })

  override def transform() = ServiceCall(req => Future.successful(req))
}
