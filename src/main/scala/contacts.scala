package net.whiteants

import ru.circumflex._, core._, web._, freemarker._, mail._
import org.apache.commons.io.FileUtils
import org.apache.commons.fileupload.FileItem
import org.apache.commons.fileupload.disk.DiskFileItemFactory
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
    contact.save()
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
      contact.save()
      Notice.addInfo("edited")
      'redirect := "/contacts"
    }

    get("/~delete").and(request.body.isXHR) = ftl("/contacts/delete.p.ftl")

    delete("/?") = partial {
      contact.notes.get.map { note =>
        FileUtils.deleteQuietly(note.path)
        note.files.children.map { file =>
          FileUtils.deleteQuietly(new File(note.baseDir, file.fileName))
        }
      }
      contact.DELETE_!()
      Notice.addInfo("deleted")
      'redirect := "/contacts"
    }

    sub("/notes") = {

      def editNote(note: Note) {
        val title = param("t").trim
        val noteParam = param("n").trim
        if (!title.isEmpty) {
          note._title := title
          FileUtils.writeStringToFile(note.path, noteParam)
          if (param("uuid") != "") {
            val fd = new FileDescription
            fd._uuid := param("uuid")
            fd._ext := param("ext")
            fd._originalName := param("originalName")
            note.files.add(fd)
            try {
              val dir = new File(uploadsDir, request.session.id.getOrElse(""))
              val srcFile = new File(dir, fd.fileName)
              val destFile = new File(note.baseDir, fd.fileName)
              FileUtils.moveFile(srcFile, destFile)
            } catch {
              case e: Exception => Notice.addError("Error")
            }
          }
          contact._notes := contact.notes.toXml
          contact.save()
          Notice.addInfo("saved")
        } else Notice.addError("contact.notes.title.empty")
      }

      get("/?") = ftl("/contacts/notes/list.ftl")

      get("/~new") = ftl("/contacts/notes/new.ftl")

      post("/?") = partial {
        val note = new Note(contact.notes)
        contact.notes.add(note)
        editNote(note)
        if (!Notice.hasErrors) 'redirect := prefix
      }

      sub("/:uuid") = {
        val note = contact.notes.getByUuid(param("uuid")).getOrElse(sendError(404))
        'note := note

        get("/?") = ftl("/contacts/notes/view.ftl")

        get("/~edit") = ftl("/contacts/notes/edit.ftl")

        post("/?") = partial {
          editNote(note)
          if (!Notice.hasErrors) 'redirect := prefix
        }

        get("/~email") = {
          ftl("/contacts/notes/send-email.p.ftl")
        }

        post("/~email") = partial {
          val msg = new MailMessage
          msg.setSubject(param("t").trim)
          msg.setHtml(markeven.toHtml(param("n").trim))
          msg.addTo(param("e").trim)
          val msgs = MailWorker.send(msg)
          if (msgs.size > 0)
            Notice.addError("mail.send.fail")
          else
            Notice.addInfo("mail.send.success")
          'redirect := prefix
        }

        get("/~delete").and(request.body.isXHR) = ftl("/contacts/notes/delete.p.ftl")

        delete("/?") = partial {
          FileUtils.deleteQuietly(note.path)
          note.files.children.map { file =>
            FileUtils.deleteQuietly(new File(note.baseDir, file.fileName))
          }
          contact.notes.delete(note)
          contact._notes := contact.notes.toXml
          contact.save()
          Notice.addInfo("deleted")
          'redirect := "/contacts/" + contact.id() + "/notes/"
        }

        sub("/file") = {

          sub("/:uuid") = {
            val file = note.find(param("uuid")).getOrElse(sendError(404))
            'file := file

            get("/?") = sendFile(new File(note.baseDir, file.fileName), file.originalFileName)

            get("/~delete") = ftl("/contacts/notes/delete-file.p.ftl")

            delete("/?") = partial {
              note.files.delete(file)
              FileUtils.deleteQuietly(new File(note.baseDir, file.fileName))
              contact._notes := contact.notes.toXml
              contact.save()
              Notice.addInfo("deleted")
              'redirect := "/contacts/" + contact.id() + "/notes/" + note.uuid
            }
          }
        }
      }
    }

  }
}