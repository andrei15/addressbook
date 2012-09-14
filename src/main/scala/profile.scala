package net.whiteants

import ru.circumflex._, core._, web._, freemarker._

class ProfileRouter extends Router {

  requireAuth()

  get("/?") = ftl("/profile/view.ftl")

  post("/?").and(request.body.isXHR) = {
    currentUser.login := param("n")
    currentUser.email := param("e")
    try {
      currentUser.save()
      setCookie(currentUser)
      'redirect := "/profile"
      Notice.addInfo("saved")
    } catch {
      case e: ValidationException =>
        currentUser.refresh()
        Notice.addErrors(e.errors)
    }
    sendJSON("/json.ftl")
  }
}