package net.whiteants

import ru.circumflex._, core._, web._, freemarker._
import java.util.Date

class Main extends Router {

  'currentDate := new Date

  'currentUser := currentUserOption

  get("/test") = "I'm fine, thanks!"
  get("/") = ftl("index.ftl")

  sub("/auth") = new AuthRouter

  sub("/addressbook") = {
    get("/:id") = {
      try {
        val user = currentUserOption.getOrElse(sendError(404))
        val id = param("id").trim.toLong
        if (user.id() == id)
          ftl("/addressbook/view.ftl")
        else sendError(404)
      } catch {
        case e: Exception =>
          sendError(404)
      }
    }


  }



}