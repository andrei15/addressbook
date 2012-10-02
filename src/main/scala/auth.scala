package net.whiteants

import ru.circumflex._, core._, web._, freemarker._
import scala.Some

object auth {
  def returnTo = flash.getAs[String]("returnTo").getOrElse("/")

  def getCookieValue(ip: String, user: User) = user.login() + ":" + sha256(ip + user.password())

  def setCookie(user: User) {
    val ip = getIpForCookie
    val c = HttpCookie("auth", getCookieValue(ip, user), path = "/", maxAge = 31 * 24 * 60 * 60)
    cookies += "auth" -> c
  }
}

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
        auth.setCookie(u)
        session.update("principal", u)
        'redirect := auth.returnTo
      case _ =>
        Notice.addError("user.not-found")
    }
  }

  get("/signup/?") = ftl("/auth/signup.ftl")

  post("/signup") = partial {
    val passw = param("p").trim
    val u = new User
    u.login := param("l")
    if (passw == "")
      throw new ValidationException("User.password.empty")
    u.password := User.getSha256Password(passw)
    u.email := param("e")
    u.save()
    auth.setCookie(u)
    session.update("principal", u)
    'redirect := auth.returnTo
    Notice.addInfo("registered")
  }
}