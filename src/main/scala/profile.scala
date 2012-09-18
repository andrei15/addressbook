package net.whiteants

import ru.circumflex._, core._, web._, freemarker._

class ProfileRouter extends Router {

  requireAuth()

  get("/?") = ftl("/profile/view.ftl")

  post("/?") = partial {
    partial.recovers.append (() => currentUser.refresh())
    currentUser.login := param("n")
    currentUser.email := param("e")
    currentUser.save()
    setCookie(currentUser)
    'redirect := "/profile"
    Notice.addInfo("saved")
  }
}