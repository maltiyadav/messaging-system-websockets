package actors

import play.api.libs.json.{Writes, JsPath, JsValue, JsString, JsObject, Json}
import play.api.libs.functional.syntax._
import play.twirl.api.HtmlFormat

case class UsersMessage(topic: String, user: String, text: String, created: java.util.Date)

object UsersMessage {
  implicit val chatMessageWrites = new Writes[UsersMessage] {
    def writes(chatMessage: UsersMessage): JsValue = {
      Json.obj(
        "type" -> "message",
        "topic" -> chatMessage.topic,
        "user" -> chatMessage.user,
        "text" -> multiLine(chatMessage.text)
      )
    }
  }

  private def multiLine(text: String) = {
    HtmlFormat.raw(text).body.replace("\n", "<br/>")
  }
}

