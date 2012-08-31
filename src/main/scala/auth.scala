package net.whiteants

import ru.circumflex._, core._, web._, freemarker._

class AuthRouter extends Router {

  get("/login/?") = ftl("/auth/login.ftl")

  get("/logout") = {
    session.remove("principal")
    sendRedirect("/")
  }

  post("/login") = {
    User.find(param("l").toLowerCase, param("p")) match {
      case Some(u: User) =>
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
    u.email:=param("e")
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