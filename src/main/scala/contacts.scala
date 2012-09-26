package net.whiteants

import ru.circumflex._, core._, web._, freemarker._
import org.apache.commons.io.FileUtils
import org.apache.commons.fileupload.FileItem
import org.apache.commons.fileupload.disk.DiskFileItemFactory
import java.io.File

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

  post("/?") = partial {
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

    get ("/~delete") = ftl("/contacts/delete.p.ftl")

    delete("/?") = {
      contact.DELETE_!()
      Notice.addInfo("deleted")
      sendRedirect("/contacts")
    }

    def editNote(note: Note) {
      if (!request.body.isMultipart) sendError(404)
      request.body.parseFileItems(new DiskFileItemFactory()).foreach { fi =>
        if (fi.isFormField) {
          ctx.update(fi.getFieldName, fi.getString("utf-8"))
        } else
          ctx.update(fi.getFieldName, fi)
      }
      val title = ctx.getString("t").getOrElse(sendError(404))
      val noteParam = ctx.getString("n").getOrElse(sendError(404))
      if (!title.isEmpty) {
        note._title := title
        FileUtils.writeStringToFile(note.path, noteParam)
        ctx.getAs[FileItem]("file").map { fi =>
          if (!fi.getName.isEmpty) {
            val uploadFile = fi.getName
            val file = new FileDesctiption
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
      }
      else Notice.addError("contact.notes.title.empty")
    }

    sub("/notes") = {

      get("/?") = {
        val notes = contact.notes.children.toList
        'notes := notes
        ftl("/contacts/notes/list.ftl")
      }

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

        get("/?") = {
          if (!param("f").isEmpty) {
            try{
              response.contentType("")
              sendFile(new File(note.baseDir, param("f")))
            } catch {
              case e:  Exception =>
            }
          }
          ftl("/contacts/notes/view.ftl")
        }

        get("/~edit") = ftl("/contacts/notes/edit.ftl")

        post("/?") = {
          editNote(note)
          if (Notice.hasErrors)
            sendRedirect(prefix + note.uuid + "/~edit")
          else sendRedirect(prefix)
        }

        get("/~delete") = ftl("/contacts/notes/delete.p.ftl")

        delete("/?") = {
          FileUtils.deleteQuietly(note.path)
          note.files.children.map { file =>
            FileUtils.deleteQuietly(new File(note.baseDir, file.fileName))
          }
          contact.notes.delete(note)
          contact._notes := contact.notes.toXml
          contact.save()
          Notice.addInfo("deleted")
          sendRedirect("/contacts/" + contact.id() +"/notes/")
        }
      }
    }
  }
}