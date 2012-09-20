package net.whiteants

import ru.circumflex._, core._, web._, freemarker._

class ContactsRouter extends Router {

  requireAuth()

  get("/?") = {
    if (!param("q").isEmpty) {
      'contacts := Contacts.userSearch(currentUser, param("q"))
    }
    else {
      'contacts := Contacts.findAll(currentUser)
    }
    ftl("/contacts/list.ftl")
  }

  get("/~new") = ftl("/contacts/new.ftl")

  post("/?")  = partial {
    val contact = new Contacts
    contact.owner := currentUser
    contact.name := param("n")
    contact.surname := param("s")
    contact.phone := param("p")
    contact.address := param("a")
    contact.email := param("e")
    contact.save()
    'redirect := prefix
    Notice.addInfo("saved")
  }

  sub("/:id") = {

    val contact = Contacts.fetch(param("id"))
    if (contact.owner() != currentUser)
      sendError(404)

    partial.addRecovers(() => contact.refresh())

    'contact := contact

    get("/?") = ftl("/contacts/view-contact.ftl")

    get("/~edit").and(request.body.isXHR) = ftl("/contacts/edit.p.ftl")

    post("/?") = partial {
      contact.name := param("n")
      contact.surname := param("s")
      contact.phone := param("p")
      contact.address := param("a")
      contact.email := param("e")
      contact.save()
      'redirect := "/contacts"
      Notice.addInfo("edited")
    }

    get ("/~delete") = ftl("/contacts/delete.p.ftl")

    delete("/?") = {
      contact.DELETE_!()
      Notice.addInfo("deleted")
      sendRedirect("/contacts")
    }

    sub("/notes") = {

      get("/~new") = ftl("/contacts/notes/new.ftl")

      post("/?")  = partial {
        val note = new Note(contact.notes)
        note._title := param("t")
        //note.path := //load file text note
        // TODO
        contact.notes.add(note)
        contact._notes := contact.notes.toXml
        contact.save()
        'redirect := prefix + note.uuid
        Notice.addInfo("saved")
      }

      sub(":uuid") = {
        //        val note = contact.notes.getByUuid(param("uuid")).getOrElse(sendError(404))
        //        'note := note
        get("/?") = ftl("/contacts/notes/view-notes.ftl")
      }
    }
  }
}