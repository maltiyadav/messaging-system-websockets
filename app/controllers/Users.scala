package controllers

import javax.inject._

import actors.{WebSockets, UsersArea}
import akka.actor._
import play.api.Play.current
import play.api.data.Form
import play.api.data.Forms._
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.libs.json.JsValue
import play.api.mvc.{Action, Controller, WebSocket}

import scala.concurrent.Future

@Singleton
class Users @Inject()(val messagesApi: MessagesApi, system: ActorSystem) extends Controller with I18nSupport {
  val User = "user"

  val nickForm = Form(single("nickname" -> nonEmptyText))

  def index = Action { implicit request =>
    request.session.get(User).map { user =>
      Redirect(routes.Users.chat()).flashing("info" -> s"Redirected to chat as $user user")
    }.getOrElse(Ok(views.html.index(nickForm)))
  }

  def name = Action { implicit request =>
    nickForm.bindFromRequest.fold(
      formWithErrors => {
        BadRequest(views.html.index(formWithErrors))
      },
      nickname => {
        Redirect(routes.Users.chat())
          .withSession(request.session + (User -> nickname))
      }
    )
  }

  def leave = Action { implicit request =>
    Redirect(routes.Users.index()).withNewSession.flashing("success" -> "See you soon!")
  }

  def chat = Action { implicit request =>
    request.session.get(User).map { user =>
      Ok(views.html.chat(user))
    }.getOrElse(Redirect(routes.Users.index()))
  }

  def socket = WebSocket.tryAcceptWithActor[JsValue, JsValue] { implicit request =>
    Future.successful(request.session.get(User) match {
      case None => Left(Forbidden)
      case Some(uid) => Right(WebSockets.props(uid))
    })
  }
}
