package net.whiteants

import ru.circumflex._, core._, web._, freemarker._

class AddressBookRouter extends Router {

  sub("/:id") = {
    if (currentUserOption.isEmpty)
      sendError(404)

    if (currentUser.id().toString != param("id"))
      sendError(404)

    get("/?") = {
      'contacts := AddressBook.findAll(currentUser)
      ftl("/addressbook/view.ftl")
    }
    get("/~new") = ftl("/addressbook/new.ftl")

    post("/") = {
      val addressBook = new AddressBook
      addressBook.owner := currentUser
      addressBook.name := param("n")
      addressBook.surname:=param("s")
      addressBook.phone:=param("p")
      addressBook.address:=param("a")
      addressBook.email:=param("e")
      try {
        addressBook.save()
      } catch {
        case e: ValidationException =>
          flash.update("errors", e.errors)
          sendRedirect(prefix + "/~new")
      }
      sendRedirect(prefix)
    }

    get("/contact/:recid/~edit")=
      {

        ftl("/addressbook/contact/edit.ftl")
      }
    get("/contact/:recid/~delete")=
      {
        param("recid").toLong
        ftl("/addressbook/contact/edit.ftl")
      }
    delete("/?") = sendError(501)
  }
}