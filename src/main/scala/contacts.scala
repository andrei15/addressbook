package net.whiteants

import ru.circumflex._, core._, web._, freemarker._

class ContactsRouter extends Router {

  requireAuth()

  get("/?") = {
    'contacts := AddressBook.findAll(currentUser)
    ftl("/contacts/list.ftl")
  }

  get("/~new") = ftl("/contacts/new.ftl")

  get("/search/?") = {
    'search := AddressBook.userSearch(currentUser,param("q"))
    ftl("/contacts/search.ftl")
  }

  post("/?") = {
    val addressBook = new AddressBook
    addressBook.owner := currentUser
    addressBook.name := param("n")
    addressBook.surname := param("s")
    addressBook.phone := param("p")
    addressBook.address := param("a")
    addressBook.email := param("e")
    try {
      addressBook.save()
      Notice.addInfo("saved")
    } catch {
      case e: ValidationException =>
        Notice.addErrors(e.errors)
        sendRedirect(prefix + "/~new")
    }
    sendRedirect(prefix)
  }

  sub("/:id") = {

    val contact = AddressBook.fetch(param("id"))
    if (contact.owner() != currentUser)
      sendError(404)

    'contact := contact

    get("/?") = ftl("/contacts/view-contact.ftl")

    get("/~edit").and(request.body.isXHR) = ftl("/contacts/edit.p.ftl")

    post("/?").and(request.body.isXHR) = {
      contact.name := param("n")
      contact.surname := param("s")
      contact.phone := param("p")
      contact.address := param("a")
      contact.email := param("e")
      try {
        contact.save()
        'redirect := "/contacts"
        Notice.addInfo("edited")
      } catch {
        case e: ValidationException =>
          Notice.addErrors(e.errors)
      }
      sendJSON("/json.ftl")
    }

    get ("/~delete") = ftl("/contacts/delete.p.ftl")

    delete("/?") = {
      contact.DELETE_!()
      Notice.addInfo("deleted")
      sendRedirect("/contacts")
    }
  }
}