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
        'redirect := flash.getAs[String]("returnTo").getOrElse("/") //redirectWithReturn
      case _ =>
        Notice.addError("user.not-found")
    }
  }

  get("/signup/?") = ftl("/auth/signup.ftl")

  post("/signup") = partial {
    val u = new User
    u.login := param("l")
    u.password := param("p")
    u.email := param("e")
    u.save()
    session.update("principal", u)
    'redirect := flash.getAs[String]("returnTo").getOrElse("/")
    Notice.addInfo("registered")
  }
}