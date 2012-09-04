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
    User.findLogin(param("n").toLowerCase) match{
      case Some(u: User) =>
        flash.update("errors", new ValidationException(Msg("User.login.unique").toString()).errors)
        sendRedirect(prefix+"/~edit")
      case _ =>
        currentUser.login := param("n")
        currentUser.email:=param("e")
        try {
          currentUser.save()
        } catch {
          case e: ValidationException =>
            flash.update("errors", e.errors)
            sendRedirect(prefix)
        }

    }
    sendRedirect("/profile")
  }
}