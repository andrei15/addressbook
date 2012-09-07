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
    response.contentType("application/json")
    try {
      currentUser.save()
      setCookie(currentUser)
      """
       {
       "msg": "saved",
       "redirect": "/profile"
       }
      """
    } catch {
      case e: ValidationException =>
        flash.update("errors", e.errors)
        currentUser.refresh()
        "{ \"errors\": [" + e.errors.map("\"" + _.toString + "\"").mkString(",") + "] }"
    }
  }
}