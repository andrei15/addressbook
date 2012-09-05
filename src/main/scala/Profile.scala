package net.whiteants

import ru.circumflex._, core._, web._, freemarker._

class ProfileRouter extends Router {
  requireAuth()

  get("/?") = {
    ftl("/profile/view.ftl")
  }

  get("/~edit")={
    ftl("/profile/edit.ftl")
  }

  post("/~edit")={
//    if (currentUser.login() == param("n"))
    if (!User.checkLoginEmail(param("n").toLowerCase,param("e")))
    {
      flash.update("errors", new ValidationException(Msg("User.login.unique").toString()).errors)
      sendRedirect(prefix+"/~edit")
    }
    else {
      currentUser.login := param("n")
      currentUser.email := param("e")
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