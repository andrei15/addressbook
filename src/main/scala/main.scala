package net.whiteants

import ru.circumflex._, core._, web._, freemarker._

class Main extends Router {

  if (!currentUserOption.isEmpty)
    rewrite("/?") = "/contacts"
  else tryCookieAuth


  get("/") = ftl("/index.ftl")

  sub("/auth") = new AuthRouter

  sub("/contacts") = new ContactsRouter

  sub("/profile") = new ProfileRouter

}