package net.whiteants

import ru.circumflex._, ru.circumflex.core._, orm._, web._, xml._
import java.io.File
import org.apache.commons.io.FileUtils

class Notes(val contact: Contacts) extends ListHolder[Note] { notes =>
  def elemName = "notes"

  def read = {
    case "note" => new Note(notes)
  }

  def getByUuid(uuid: String) = children.find(_.uuid == uuid)

}

class Note(@transient val notes: Notes) extends StructHolder {

  val baseDir = new File("/var/addressbook/" + notes.contact.owner.value.get.id())

  def elemName = "note"

  val _title = attr("title")
  def title = _title.getOrElse("")

  val _uuid = attr("uuid").set(randomUUID)
  def uuid = _uuid.getOrElse {
    _uuid := randomUUID
    _uuid()
  }

  lazy val path = new File(baseDir, uuid)

  val files = new ListHolder[FileDesctiption] {
    def elemName = "files"
    def read = {
      case "file" => new FileDesctiption
    }
  }

  def getNote = FileUtils.readFileToString(path)
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

  def fileName = uuid + "." + ext
}
