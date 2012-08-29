package net.whiteants

import ru.circumflex._, core._, web._, freemarker._

class AuthRouter extends Router {

  get("/login/?") = ftl("/auth/login.ftl")

  post("/login") = {
    val user = User.find(param("l"), param("p"))
    if (user.isEmpty) {
      flash.update("error", new Msg("user.not-found"))
      sendRedirect("/login")
    } else {
      session.update("principal", user.get)
      sendRedirect("/addressbook/" + user.get.id())
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
    sendRedirect("/")
  }
}