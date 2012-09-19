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

  post("/login") = partial {
    User.find(param("l").toLowerCase, param("p")) match {
      case Some(u: User) =>
        //set cookie
        setCookie(u)
        session.update("principal", u)
        'redirect := redirectWithReturn
      case _ =>
        Notice.addError("user.not-found")
    }
  }

  get("/signup/?") = ftl("/auth/signup.ftl")

  post("/signup") = partial {
    val passw = param("p")
    val u = new User
    u.login := param("l")
    u.password := ""
    if (!passw.isEmpty)
      u.password := User.getSha256Password(passw)
    u.email := param("e")
    u.save()
    setCookie(u)
    session.update("principal", u)
    'redirect := redirectWithReturn
    Notice.addInfo("registered")
  }
}