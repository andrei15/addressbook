package net.whiteants

import ru.circumflex._, ru.circumflex.core._, orm._, web._, xml._
import java.io.{Writer, File}
import markeven.LinkDef
import org.apache.commons.io.FileUtils

class Notes(val contact: Contact) extends ListHolder[Note] {notes =>
  def elemName = "notes"

  def read = {
    case "note" => new Note(notes)
  }

  def getByUuid(uuid: String) = children.find(_.uuid == uuid)

  def get = children
}

class Note(@transient val notes: Notes)
  extends StructHolder {note =>
  def elemName = "note"

  val _title = attr("title")
  def title = _title.getOrElse("")

  val _uuid = attr("uuid").set(randomUUID)
  def uuid = _uuid.getOrElse {
    _uuid := randomUUID
    _uuid()
  }

  lazy val baseDir = new File("/var/addressbook/" + notes.contact.owner.value.get.id() + "/" + note.uuid)

  lazy val path = new File(baseDir, "note")

  val files = new ListHolder[FileDescription] {
    def elemName = "files"

    def read = {
      case "file" => new FileDescription
    }
  }

  val resources = new ListHolder[Res] {
    def elemName = "resources"

    def read = {
      case "link" => new LinkRes(note)
      case "video" => new VideoRes(note)
      case "img" => new ImgRes(note)
    }

    def getById(id: String) = children.find(_.id == id)
  }

  def plainText = if (path.exists && path.isFile) {
    FileUtils.readFileToString(path)
  } else ""

  lazy val renderer = new NoteRenderer(note)

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

  def note: Note
}

class LinkRes(@transient val note: Note) extends Res {
  def elemName = "link"
  def updateFromParams() {
    _url := param("url").trim
  }
}

class ImgRes(@transient val note: Note) extends Res {
  def elemName = "img"

  def updateFromParams() {
    val resDir = new File(note.baseDir.toString, "/resources/img")
    val uuid = param("uuid")
    val ext = param("ext")
    val resName = uuid + "." + ext

    if (!uuid.isEmpty) {
      try {
        val dir = new File(uploadsDir, request.session.id.getOrElse(""))
        val srcFile = new File(dir, resName)
        val destFile = new File(resDir.toString + "/", resName)
        FileUtils.moveFile(srcFile, destFile)
      } catch {
        case e: Exception => Notice.addError("Error")
      }
      this._url := resDir.toString + "/" + resName
    }
  }
}

class VideoRes(@transient val note: Note) extends Res {
  def elemName = "video"

  def linkDef = new LinkDef(url, title) {
    override def writeMedia(w: Writer, alt: String) {
      val s =
        """
          <iframe class="youtube-player"
                  type="text/html"
                  width=%s
                  height=%s
                  src="http://www.youtube.com/embed/%s"
                  frameborder="0">%s</iframe>
        """.stripMargin.format("480", "250", url, title)
      w.write(s)
    }
  }

  def updateFromParams() {
    val url = param("url")
    val check = Regex.youtubePattern.matcher(url)
    if (check.lookingAt()) {
      _url := check.group(1)
    } else throw new ValidationException("resources.url.notValid")
  }
}