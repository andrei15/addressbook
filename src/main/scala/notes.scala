package net.whiteants

import ru.circumflex._, ru.circumflex.core._, orm._, web._, xml._
import java.io.File
import org.apache.commons.io.FileUtils

class Notes(val contact: Contact) extends ListHolder[Note] {notes =>
  def elemName = "notes"

  def read = {
    case "note" => new Note(notes)
  }

  def getByUuid(uuid: String) = children.find(_.uuid == uuid)

  def get = children
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

  val files = new ListHolder[FileDescription] {
    def elemName = "files"

    def read = {
      case "file" => new FileDescription
    }
  }

  val resources = new ListHolder[Res] {
    def elemName = "resources"

    def read = {
      case "link" => new LinkRes
      case "video" => new VideoRes
      case "img" => new ImgRes
    }
  }

  def plainText = if (path.exists && path.isFile) {
    FileUtils.readFileToString(path)
  } else ""

  lazy val renderer = new NoteRenderer(this.notes)

  def html = renderer.toHtml(plainText)

  def find(uuid: String) = {
    files.children.find(_.uuid == uuid)
  }
}

class FileDescription extends StructHolder {

  def elemName = "file"

  val _uuid = attr("uuid")

  def uuid = _uuid.getOrElse {
    _uuid := randomUUID
    _uuid()
  }

  val _originalName = attr("originalName")

  def originalName = _originalName.getOrElse("")

  val _ext = attr("ext")

  def ext = _ext.getOrElse("")

  def fileName = uuid + "." + ext

  def originalFileName = originalName + "." + ext
}

class Res extends StructHolder {
  def elemName = "resource"
}

class LinkRes extends Res {

}

class VideoRes extends Res {

}

class ImgRes extends Res {

}