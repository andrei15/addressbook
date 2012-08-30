package net.whiteants

import ru.circumflex._, core._, web._, freemarker._
import java.util.Date

class Main extends Router {

  'currentDate := new Date

  'currentUser := currentUserOption

  get("/test") = "I'm fine, thanks!"
  get("/") = ftl("index.ftl")

  sub("/auth") = new AuthRouter
  sub("/addressbook") = new AddressBookRouter

}