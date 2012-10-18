package net.whiteants

import ru.circumflex._, core._, web._, orm._, freemarker._, mail._
import org.apache.commons.io.FileUtils
import java.io.File

class ContactsRouter extends Router {

  requireAuth()

  get("/?") = {
    if (!param("q").trim.isEmpty) {
      'contacts := Contact.userSearch(currentUser, param("q").trim)
    } else {
      'contacts := Contact.findAll(currentUser)
    }
    ftl("/contacts/list.ftl")
  }

  get("/~new") = ftl("/contacts/new.ftl")

  post("/?") = partial {
    val contact = new Contact
    contact.owner := currentUser
    contact.name := param("n").trim
    contact.surname := param("s").trim
    contact.phone := param("p").trim
    contact.address := param("a").trim
    contact.email := param("e").trim
    using(db.master) {
      contact.save()
    }
    Notice.addInfo("saved")
    'redirect := prefix
  }

  sub("/:id") = {
    val contact = Contact.fetch(param("id"))
    if (contact.owner() != currentUser)
      sendError(404)

    partial.addRecovers(() => contact.refresh())

    'contact := contact

    get("/?") = ftl("/contacts/view.ftl")

    get("/~edit").and(request.body.isXHR) = ftl("/contacts/edit.p.ftl")

    post("/?") = partial {
      contact.name := param("n").trim
      contact.surname := param("s").trim
      contact.phone := param("p").trim
      contact.address := param("a").trim
      contact.email := param("e").trim
      using(db.master) {
        contact.save()
      }
      Notice.addInfo("edited")
      'redirect := "/contacts"
    }

    get("/~delete").and(request.body.isXHR) = ftl("/contacts/delete.p.ftl")

    delete("/?") = partial {
      using(db.master) {
        contact.DELETE_!()
      }
      contact.notes.get.map { note =>
        FileUtils.deleteQuietly(note.path)
        FileUtils.deleteQuietly(note.baseDir)
        note.files.children.map { file =>
          FileUtils.deleteQuietly(new File(note.baseDir, file.fileName))
        }
      }
      Notice.addInfo("deleted")
      'redirect := "/contacts"
    }

    sub("/notes") = {

      get("/?") = ftl("/contacts/notes/list.ftl")

      get("/~new") = ftl("/contacts/notes/new.ftl")

      post("/?") = partial {
        val note = new Note(contact.notes)
        contact.notes.add(note)
        editNote(note, contact)
        if (!Notice.hasErrors) 'redirect := prefix
      }

      sub("/:uuid") = new NoteRouter(contact)

    }
  }
}