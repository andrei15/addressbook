package net.whiteants

import ru.circumflex._, ru.circumflex.core._, orm._, web._, xml._
import java.io.File

class Notes(val contact: Contacts) extends ListHolder[Note] { notes =>
  def elemName = "notes"

  def read = {
    case "note" => new Note(notes)
  }

//  def getByUuid(uuid: String): Option[Note] = notes.contact._notes.uuid match {
//    case Some(n: Note) => Some(n)
//    case _ => None
//  }
}

class Note(@transient val notes: Notes)
  extends StructHolder {

  val baseDir = new File("/var/addressbook/" + notes.contact.owner.value.get.id()) // TODO: Implement me

  def elemName = "note"

  val _title = attr("title")
  def title = _title.getOrElse("")

  val _uuid = attr("uuid").set(randomUUID)
  def uuid = _uuid.getOrElse {
    _uuid := randomUUID
    _uuid()
  }

  def path = new File(baseDir, uuid)


  val files = new ListHolder[FileDesctiption] {
    def elemName = "files"
    def read = {
      case "file" => new FileDesctiption
    }
  }
}

class FileDesctiption extends StructHolder {
  def elemName = "file"

  val _uuid = attr("uuid")
  def uuid = _uuid.getOrElse {
    _uuid := randomUUID
    _uuid()
  }
  val _ext = attr("ext")
  def ext = _ext.getOrElse("")

}