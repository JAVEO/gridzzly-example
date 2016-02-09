package filters

import play.api.Play
import play.api.mvc._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object CorsHeadersFilter extends Filter {
  def apply(f: (RequestHeader) => Future[Result])(rh: RequestHeader): Future[Result] =
    f(rh).map(_.withHeaders(
      "Access-Control-Allow-Origin"  -> "http://localhost:3002",
      "Access-Control-Allow-Headers" -> "Accept, Authorization, Content-Type, Auth-Token",
      "Access-Control-Allow-Methods" -> "GET, POST, DELETE, OPTIONS, PUT, PATCH"
    ))
}