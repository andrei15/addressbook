package net

import ru.circumflex._, core._, web._

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

  def setCookie(u:User){
    var ip = request.remoteIp
    val pos =  ip.lastIndexOf('.')
    if(pos != -1) ip = ip.substring(0, pos)
    val c = HttpCookie("auth", u.getCookie(ip), path = "/", maxAge = 86400)
    cookies += "auth" -> c
  }

  def requireCookieAuth() {
    val cookie = request.cookies.find(_.name == "auth")
    if (!cookie.isEmpty ){
      val c = cookie.get.value
      var pos = c.indexOf(":")
      var login = ""
      var sha = ""
      if (pos != -1)
      {
        login = c.substring(0, pos)
        sha = c.substring(c.indexOf(":") + 1, c.length)
      }
      var ip = request.remoteIp
      pos = ip.lastIndexOf('.')
      if (pos != -1)
      {
        ip = ip.substring(0, ip.lastIndexOf('.'))
      }
      User.findLogin(login) match {
        case Some(u: User) =>
          if (sha == sha256(ip+u.password())){
            session.update("principal", u)
          }
          else flash.update("error", new Msg("user.not-found"))
        case _ =>
          flash.update("error", new Msg("user.not-found"))
        //sendRedirect("/auth/login")
      }
    }
  }
}

