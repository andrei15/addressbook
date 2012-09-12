package net.whiteants

import ru.circumflex._, core._, web._, freemarker._

class AuthRouter extends Router {

  get("/login/?") = ftl("/auth/login.ftl")

  get("/logout") = {
    session.remove("principal")
    //remove cookie
    val c = HttpCookie("auth", "", path = "/", maxAge = 0)
    cookies += "auth" -> c
    sendRedirect("/")
  }

  post("/login") = {
    User.find(param("l").toLowerCase, param("p")) match {
      case Some(u: User) =>
        //set cookie
        setCookie(u)
        session.update("principal", u)
        redirectWithReturn
      case _ =>
        Notice.addError("user.not-found")
        // flash.update("error", new Msg("user.not-found"))
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
      Notice.addInfo("registered")
    } catch {
      case e: ValidationException =>
        Notice.addErrors(e.errors)
        //flash.update("errors", e.errors)
        sendRedirect("/auth/signup")
    }
    session.update("principal", u)
    redirectWithReturn
  }
}