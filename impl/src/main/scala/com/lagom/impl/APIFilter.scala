package com.lagom.impl

import akka.actor.ActorSystem
import akka.stream.Materializer
import akka.util.ByteString
import com.lagom.api.IntegrationService
import javax.inject.Inject
import play.api.http.HttpEntity
import play.api.libs.json.Json
import play.api.mvc.{Filter, RequestHeader, ResponseHeader, Result}

import scala.concurrent.{ExecutionContext, Future}

class APIFilter @Inject()(integrationService: IntegrationService)(implicit val mat: Materializer,
                                                                  implicit val ec: ExecutionContext) extends Filter {


  implicit val actorSystem: ActorSystem = ActorSystem("apifilteractor")

  override def apply(nextFilter: RequestHeader => Future[Result])(requestHeader: RequestHeader): Future[Result] = {

    nextFilter(requestHeader).map { result =>
      val str = result.body.consumeData.value.get.get.map(_.toChar).mkString
      val jsObj = Json.obj(
        "body" -> Json.parse(str),
        "method" -> requestHeader.method,
        "uri" -> requestHeader.uri,
        "media_type" -> requestHeader.mediaType.get.mediaType,
        "query_params" -> requestHeader.rawQueryString)

      val res = integrationService.internalCall(jsObj).value.get.get

      Result(header = ResponseHeader(200, Map.empty), body = HttpEntity.Strict(ByteString(res.toString()), Some("application/json")))
    }
  }
}
