package net.whiteants

import ru.circumflex._, core._, web._, freemarker._

class ContactsRouter extends Router {

  requireAuth()

  get("/?") = {
    'contacts := AddressBook.findAll(currentUser)
    ftl("/contacts/list.ftl")
  }

  get("/~new") = ftl("/contacts/new.ftl")

  get("/find?") = ftl("/contacts/find.ftl")

  post("/find?") ={
    'findr := AddressBook.userFind(param("n"),param("s"),param("p"),param("e"),param("a"))
    sendRedirect(prefix + "/findresult")
  }

  get("/findresult?") = ftl("/contacts/result-find.ftl")


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

    get("/?") = {
      'contacts := AddressBook.findAll(currentUser)
      ftl("/contacts/list.ftl")
    }

    get("/~edit") = ftl("/contacts/edit.ftl")

    post("/?") = {
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
      sendRedirect("/contacts")
    }

    get ("/~delete") = ftl("/contacts/delete.p.ftl")

    delete("/?") = {
      contact.DELETE_!()
      sendRedirect("/contacts")
    }
  }
}