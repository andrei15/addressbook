package net.whiteants

import ru.circumflex._, core._, web._, freemarker._

class ProfileRouter extends Router {

  requireAuth()

  get("/?") = {
    ftl("/profile/view.ftl")
  }

  //get("/~edit") = ftl("/profile/edit.ftl")

  post("/?").and(request.body.isXHR) = {
    currentUser.login := param("n")
    currentUser.email := param("e")
    try {
      currentUser.save()
      setCookie(currentUser)
    } catch {
      case e: ValidationException =>
        flash.update("errors", e.errors)
        currentUser.refresh()
        response.contentType("application/json")
        "{ errors: [" + e.errors.map("\"" + _.toString + "\"").mkString(",") + "] }"
    }
    response.contentType("application/json")
    """
       {
       msg: "saved",
       redirect: "/profile"
       }
    """
  }
}