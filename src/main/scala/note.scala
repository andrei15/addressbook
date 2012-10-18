package net.whiteants

import ru.circumflex._, core._, web._, orm._, freemarker._, mail._
import org.apache.commons.io.FileUtils
import java.io.File

class NoteRouter(contact: Contact) extends Router {

  val note = contact.notes.getByUuid(param("uuid")).getOrElse(sendError(404))
  'note := note

  get("/?") = ftl("/contacts/notes/view.ftl")

  get("/~edit") = ftl("/contacts/notes/edit.ftl")

  post("/?") = partial {
    editNote(note, contact)
    if (!Notice.hasErrors) 'redirect := prefix
  }

  get("/~email").and(request.body.isXHR) = ftl("/contacts/notes/send-email.p.ftl")

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
    contact.notes.delete(note)
    contact._notes := contact.notes.toXml
    using(db.master) {
      contact.save()
    }
    FileUtils.deleteQuietly(note.path)
    FileUtils.deleteQuietly(note.baseDir)
    Notice.addInfo("deleted")
    'redirect := "/contacts/" + contact.id() + "/notes/"
  }

  sub("/file") = {

    sub("/:uuid") = {
      val file = note.find(param("uuid")).getOrElse(sendError(404))
      'file := file

      get("/?") = sendFile(new File(note.baseDir, file.fileName), file.originalFileName)

      get("/~delete").and(request.body.isXHR) = ftl("/contacts/notes/delete-file.p.ftl")

      delete("/?") = partial {
        note.files.delete(file)
        contact._notes := contact.notes.toXml
        using(db.master) {
          contact.save()
        }
        FileUtils.deleteQuietly(new File(note.baseDir, file.fileName))
        Notice.addInfo("deleted")
        'redirect := "/contacts/" + contact.id() + "/notes/" + note.uuid
      }
    }
  }

  sub("/resources") = {

    get("/?") = ftl("/contacts/notes/resources/view.ftl")

    sub("/oper") = {
      sub("/:id") = {
        val res = note.resources.getById(param("id").trim)
        'res := res

        get("/~delete") = ftl("/contacts/notes/resources/delete-resources.p.ftl")

        delete("/?") = partial {
          note.resources.delete(res.get)
          contact._notes := contact.notes.toXml
          using(db.master) {
            contact.save()
          }
          Notice.addInfo("deleted")
          'redirect := "/contacts/" + contact.id() + "/notes/" + note.uuid
        }
      }
    }

    sub("/:res") = {
      val kind = param("res").trim
      'kind := kind
      if (!Seq("img", "video", "link").contains(kind))
        sendError(404)

      get("/?") = ftl("/contacts/notes/resources/" + kind + "/new.ftl")

      post("/?") = partial {
        val res: Res = kind match {
          case "img" => new ImgRes(note)
          case "link" => new LinkRes(note)
          case "video" => new VideoRes(note)
          case _ => sendError(404)
        }
        res._title := param("t").trim
        res.updateFromParams()
        note.resources.add(res)
        contact._notes := contact.notes.toXml
        using(db.master) {
          contact.save()
        }
        'redirect := "/contacts/" + contact.id() + "/notes/" + note.uuid + "/resources"
      }
    }
  }
}