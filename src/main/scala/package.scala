package net

import ru.circumflex._, core._, web._
import java.util.Date
import java.io.File

package object whiteants {

  val log = new Logger("net.whiteants")

  def currentUserOption: Option[User] = session.get("principal") match {
    case Some(u: User) => Some(u)
    case _ => None
  }

  def currentUser = currentUserOption.get

  def redirectWithReturn = {
    val returnTo = flash.getAs[String]("returnTo").getOrElse("/")
    sendRedirect(returnTo)
  }

  def requireAuth() {
    if (currentUserOption.isEmpty) {
      if (request.method == "get") {
        var location = request.raw.getRequestURI
        if (request.queryString != "")
          location += "?" + request.raw.getQueryString
        flash("returnTo") = location
        sendRedirect("/auth/login")
      } else sendError(404)
    }
  }

  def tryCookieAuth() {
    if (!request.cookies.find(_.name == "auth").isEmpty ){
      val c=request.cookies.find(_.name=="auth").get.value
      User.findLogin(c) match {
        case Some(u: User) =>
          session.update("principal", u)
          redirectWithReturn
        case _ =>
          flash.update("error", new Msg("user.not-found"))
          //sendRedirect("/auth/login")
      }
    }

  }
}

