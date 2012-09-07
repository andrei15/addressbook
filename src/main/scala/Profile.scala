package net.whiteants

import ru.circumflex._, core._, web._, freemarker._

class ProfileRouter extends Router {

  requireAuth()

  get("/?") = ftl("/profile/view.ftl")

  post("/?").and(request.body.isXHR) = {
    currentUser.login := param("n")
    currentUser.email := param("e")
    response.contentType("application/json")
    try {
      currentUser.save()
      setCookie(currentUser)
      //      notice.addMsg("msg","saved")
      """
       {
      "msg": "saved",
       "redirect": "/profile"
       }
      """
    } catch {
      case e: ValidationException =>
        currentUser.refresh()
        "{ \"errors\": [" + e.errors.map("\"" + _.toString + "\"").mkString(",") + "] }"
    }
  }
}