package net.whiteants

import ru.circumflex._, ru.circumflex.core._, orm._, web._, xml._
import java.io.{Writer, File}
import markeven.LinkDef
import org.apache.commons.io.FileUtils
import java.util.regex.Pattern

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

    def getById(id: String) = children.find(_.id == id)
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

trait Res extends StructHolder {
  val _id = attr("id").set(core.randomString(8))
  def id = _id.getOrElse("")

  val _url = attr("url")
  def url = _url.getOrElse("/")

  val _title = attr("title")
  def title = _title.getOrElse("")

  def updateFromParams()
}

class LinkRes extends Res {
  def elemName = "link"
  def updateFromParams() {
    _url := param("url")
  }
}

class VideoRes extends Res {
  def elemName = "video"

  def linkDef = new LinkDef(id, title) {
    override def writeMedia(w: Writer, alt: String) {
      w.write("<iframe class=\"youtube-player\" type=\"text/html\"")
      w.write("src=\"http://www.youtube.com/embed/" + id + "\"")
      w.write("frameborder=\"0\">" + title + "</iframe>\"\"\"")
    }
  }
  def updateFromParams() {
    val url = param("n")
    val youtubePattern =
      Pattern.compile("^(?i:(?:https?://)?[a-z0-9_.-]*?\\.?youtu(?:\\.be|be\\.com))/(?:.*v(?:/|=)|(?:.*/)?)" +
        "([a-zA-Z0-9-_]+)")
    val check = youtubePattern.matcher(url)
    if (check.lookingAt()) {
      _url := param("n")
    }
  }
}

class ImgRes extends Res {
  def elemName = "img"
  def updateFromParams() {
    val resDir = "/var/addressbook/" + currentUser.id() + "/resources/"
    val uuid = param("uuid")
    val ext = param("ext")
    if (!uuid.isEmpty) {
      try {
        val dir = new File(uploadsDir, request.session.id.getOrElse(""))
        val srcFile = new File(dir, uuid + "." + ext)
        val destFile = new File(resDir + "img/", uuid + "." + ext)
        FileUtils.moveFile(srcFile, destFile)
      } catch {
        case e: Exception => Notice.addError("Error")
      }
      this._url := resDir + "img/" + uuid + "." + ext
    }
  }
}