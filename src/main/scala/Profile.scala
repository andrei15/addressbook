package net.whiteants

import ru.circumflex._, core._, web._, freemarker._

class ProfileRouter extends Router {
  requireAuth()

  get("/?") = {
    ftl("/profile/view.ftl")
  }

  get("/~edit")={
    ftl("/profile/edit-base.ftl")
  }

  post("/~edit")={

    currentUser.login := param("n")
    currentUser.email:=param("e")
    try {
      currentUser.save()
    } catch {
      case e: ValidationException =>
        flash.update("errors", e.errors)
        sendRedirect(prefix)
    }
    sendRedirect("/profile")
  }
}