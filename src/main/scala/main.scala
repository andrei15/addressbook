package net.whiteants

import ru.circumflex._,  web._, freemarker._

class Main extends Router {
  cookieAuth()

  if (!currentUserOption.isEmpty)
    rewrite("/?") = "/contacts"

  get("/") = ftl("/index.ftl")

  sub("/auth") = new AuthRouter

  sub("/contacts") = new ContactsRouter

  sub("/profile") = new ProfileRouter

}