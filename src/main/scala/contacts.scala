package net.whiteants

import ru.circumflex._, core._, web._, freemarker._
import org.apache.commons.io.FileUtils
import org.apache.commons.fileupload.FileItem
import org.apache.commons.fileupload.disk.DiskFileItemFactory
import java.io.File

class ContactsRouter extends Router {

  requireAuth()

  get("/?") = {
    if (!param("q").trim.isEmpty) {
      'contacts := Contact.userSearch(currentUser, param("q"))
    } else {
      'contacts := Contact.findAll(currentUser)
    }
    ftl("/contacts/list.ftl")
  }

  get("/~new") = ftl("/contacts/new.ftl")

  post("/?") = partial {
    val contact = new Contact
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
    val contact = Contact.fetch(param("id"))
    if (contact.owner() != currentUser)
      sendError(404)

    partial.addRecovers(() => contact.refresh())

    'contact := contact

    get("/?") = ftl("/contacts/view.ftl")

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
        if (!request.body.isMultipart) sendError(404)
        request.body.parseFileItems(new DiskFileItemFactory()).foreach { fi =>
          if (fi.isFormField) {
            ctx.update(fi.getFieldName, fi.getString("utf-8"))
          } else
            ctx.update(fi.getFieldName, fi)
        }
        val title = ctx.getString("t").getOrElse("")
        val noteParam = ctx.getString("n").getOrElse("")
        if (!title.isEmpty) {
          note._title := title
          FileUtils.writeStringToFile(note.path, noteParam)
          ctx.getAs[FileItem]("file").map { fi =>
            if (!fi.getName.isEmpty) {
              val uploadFile = fi.getName
              val file = new FileDescription
              note.files.add(file)
              val i = uploadFile.lastIndexOf(".")
              if (i != -1) {
                val ext = uploadFile.substring(i + 1)
                file._originalName := uploadFile.substring(0, i)
                file._ext := ext
                try {
                  fi.write(new File(note.baseDir, file.fileName))
                } catch {
                  case e: Exception => Notice.addError("Error")
                }
              }
            }
          }
          contact._notes := contact.notes.toXml
          contact.save()
          Notice.addInfo("saved")
        } else Notice.addError("contact.notes.title.empty")
      }

      get("/?") = ftl("/contacts/notes/list.ftl")

      get("/~new") = ftl("/contacts/notes/new.ftl")

      post("/?") = {
        val note = new Note(contact.notes)
        contact.notes.add(note)
        editNote(note)
        if (Notice.hasErrors)
          sendRedirect(prefix + "/~new")
        else sendRedirect(prefix)
      }

      sub("/:uuid") = {
        val note = contact.notes.getByUuid(param("uuid")).getOrElse(sendError(404))
        'note := note

        get("/?") = ftl("/contacts/notes/view.ftl")

        get("/~edit") = ftl("/contacts/notes/edit.ftl")

        post("/?") = {
          editNote(note)
          if (Notice.hasErrors)
            sendRedirect(prefix + "/~edit")
          else sendRedirect(prefix)
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