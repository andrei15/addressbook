package net.whiteants

import ru.circumflex._, core._, web._, orm._, freemarker._

object auth {
  def returnTo = flash.getAs[String]("returnTo").getOrElse("/")

  def getCookieValue(ip: String, user: User) = user.login() + ":" + sha256(ip + user.password())

  def setCookie(user: User) {
    val ip = getIpForCookie
    val c = HttpCookie("ab-auth", getCookieValue(ip, user), path = "/", maxAge = 31 * 24 * 60 * 60)
    cookies += "ab-auth" -> c
  }
}

class AuthRouter extends Router {

  get("/login/?") = ftl("/auth/login.ftl")

  get("/logout") = {
    session.remove("principal")
    //remove cookie
    val c = HttpCookie("ab-auth", "", path = "/", maxAge = 0)
    cookies += "ab-auth" -> c
    sendRedirect("/")
  }

  post("/login") = partial {
    User.find(param("l").trim.toLowerCase, param("p").trim) match {
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
    u.login := param("l").trim
    if (passw == "")
      throw new ValidationException("User.password.empty")
    u.password := User.getSha256Password(passw)
    u.email := param("e").trim
    using(db.master) {
      u.save()
    }
    auth.setCookie(u)
    session.update("principal", u)
    'redirect := auth.returnTo
    Notice.addInfo("registered")
  }
}