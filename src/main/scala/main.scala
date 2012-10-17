package net.whiteants

import ru.circumflex._, web._, freemarker._
import java.io.File

class Main extends Router {

  cookieAuth()

  if (!currentUserOption.isEmpty)
    rewrite("/?") = "/contacts"

  get("/") = ftl("/index.ftl")

  sub("/auth") = new AuthRouter

  sub("/contacts") = new ContactsRouter

  sub("/profile") = new ProfileRouter

  sub("/svc") = new SvcRouter

  any("/var/addressbook/*") = {
    val f = new File(uri(0))
    if (f.exists && f.isFile)
      sendFile(f)
    else sendError(404)
  }
}