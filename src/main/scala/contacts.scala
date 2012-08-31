package net.whiteants

import ru.circumflex._, core._, web._, freemarker._

class ContactsRouter extends Router {

  requireAuth()

  get("/?") = {
    'contacts := AddressBook.findAll(currentUser)
    ftl("/contacts/list.ftl")
  }

  get("/~new") = ftl("/contacts/new.ftl")

  post("/?") = {
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

  sub("/:id") = {

    val contact = AddressBook.fetch(param("id"))
    if (contact.owner() != currentUser)
      sendError(404)

    'contact := contact

    get("/?") = ftl("/contacts/view.ftl")

    get("/~edit") = ftl("/contacts/edit.ftl")

    post("/~edit") = {
      contact.name := param("n")
      contact.surname:=param("s")
      contact.phone:=param("p")
      contact.address:=param("a")
      contact.email:=param("e")
      try {
        contact.save()
      } catch {
        case e: ValidationException =>
          flash.update("errors", e.errors)
          sendRedirect(prefix + "/~edit")
      }
      sendRedirect(prefix)
    }
    delete("/?") = {
      try  {
        contact.DELETE_!()
      } catch {
        case e: Exception =>
          sendError(404)
      }
      sendRedirect(prefix)
    }
  }
}