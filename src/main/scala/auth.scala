package net.whiteants

import ru.circumflex._, core._, web._, freemarker._

class AuthRouter extends Router {

  get("/login/?") = ftl("/auth/login.ftl")



  get("/logout") = {
    session.remove("principal")
    //remove cookie
    val c = HttpCookie("auth", "", path = "/", maxAge = 0)
    cookies += "auth" -> c
    sendRedirect("/auth/login")
  }

  post("/login") = {
    User.find(param("l").toLowerCase, param("p")) match {
      case Some(u: User) =>
        //установка cookie
        var ip = request.remoteIp
        val pos =  ip.lastIndexOf('.')
        if(pos != -1) ip = ip.substring(0, pos)
        val c = HttpCookie("auth", u.getCookie(ip), path = "/", maxAge = 86400)
        cookies += "auth" -> c
        session.update("principal", u)
        redirectWithReturn
      case _ =>
        flash.update("error", new Msg("user.not-found"))
        sendRedirect("/auth/login")
    }
  }

  get("/signup/?") = ftl("/auth/signup.ftl")

  post("/signup") = {
    val u = new User
    u.login := param("l")
    u.password := param("p")
    u.email := param("e")
    try {
      u.save()
    } catch {
      case e: ValidationException =>
        flash.update("errors", e.errors)
        sendRedirect("/auth/signup")
    }
    session.update("principal", u)
    redirectWithReturn
  }
}