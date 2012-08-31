package net.whiteants

import ru.circumflex._, core._, web._, freemarker._
import java.util.Date

class Main extends Router {

  if (!currentUserOption.isEmpty)
    rewrite("/?") = "/contacts"

  get("/") = ftl("/index.ftl")

  sub("/auth") = new AuthRouter

  sub("/contacts") = new ContactsRouter

}