package net

import ru.circumflex._, core._, web._, freemarker._
import collection.mutable.ListBuffer
import java.io.File

package object whiteants {

  val log = new Logger("net.whiteants")

  val uploadsDir = new File(
    cx.getAs[String]("ab.uploadsDir")
      .getOrElse("/var/addressbook/uploads")
  )

  def publicRoot = new File("src/main/webapp/public")

  def currentUserOption: Option[User] = session.get("principal") match {
    case Some(u: User) => Some(u)
    case _ => None
  }

  def currentUser = currentUserOption.get

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

  def getIpForCookie = {
    val pos = request.remoteIp.lastIndexOf('.')
    if (pos != -1)
      request.remoteIp.substring(0, pos)
    else
      request.remoteIp
  }

  def cookieAuth() {
    if (!currentUserOption.isEmpty) return
    val cookie = request.cookies.find(_.name == "ab-auth")
    if (!cookie.isEmpty) {
      val c = cookie.get.value
      val pos = c.indexOf(":")
      if (pos == -1) return
      val login = c.substring(0, pos)
      val sha = c.substring(pos + 1, c.length)
      val ip = getIpForCookie
      User.findLogin(login) match {
        case Some(u: User) =>
          if (sha == sha256(ip + u.password())) {
            session.update("principal", u)
          }
        case _ =>
      }
    }
  }

  def sendJSON(templ: String): Nothing = {
    response.contentType("application/json")
    ftl(templ)
  }

  object partial {

    val recovers = new ListBuffer[() => Unit]

    def addRecovers(element: () => Unit) {
      recovers.append(element)
    }

    def apply(actions: => Unit): Nothing = {
      if (!request.body.isXHR) sendError(404)
      try {
        actions
      } catch {
        case e: ValidationException =>
          Notice.addErrors(e.errors)
          recovers.foreach(_.apply())
        case e: Exception =>
          recovers.foreach(_.apply())
          throw e
      }
      sendJSON("/json.ftl")
    }
  }

}

