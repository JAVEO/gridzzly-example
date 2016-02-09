package actions

import play.api.mvc.Results._
import play.api.mvc.{Result, Request, ActionBuilder}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class RecoverAction() extends ActionBuilder[Request] {
  override def invokeBlock[A](request: Request[A], block: (Request[A]) => Future[Result]): Future[Result] = {
    val result = Future[Future[Result]](block(request)).flatMap(identity)

    result.recover {
      case e: Exception => InternalServerError(s"Unexpected server error $e")
    }
  }
}

object RecoverAction {
  def apply(): RecoverAction = new RecoverAction()
}