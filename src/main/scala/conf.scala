package net.whiteants

import _root_.freemarker.cache.FileTemplateLoader
import ru.circumflex._, core._, freemarker._, orm._
import java.io.{Writer, File}
import java.util.Date
import markeven.{LinkDef, Renderer, MarkevenConf}

class FtlConfiguration extends DefaultConfiguration {
  setNumberFormat("0.##")
  setSharedVariable("env", env)
  setSharedVariable("me", MarkevenDirective)
  addLoader(new FileTemplateLoader(new File("src/main/webapp/templates")))
  _root_.freemarker.log.Logger.selectLoggerLibrary(_root_.freemarker.log.Logger.LIBRARY_SLF4J)
}

class ABMarkevenConf(val note: Note) extends MarkevenConf {

  def resolveLink(id: String) = {
    note.notes.getByUuid(id) match {
      case Some(note: Note) =>
        val uuid = note.uuid
        val linkCreate = new LinkCreate(uuid, note)
        Some(linkCreate.create())
      case _ =>
        note.resources.children
          .find(_.id == id) match {
          case Some(link: LinkRes) =>
            Some(new LinkDef(link.url, link.title))
          case _ => None
        }
    }
  }

  def resolveMedia(id: String) = {
    note.resources.getById(id) match {
      case Some(res: ImgRes) =>
        Some(new LinkDef(res.url, res.title))
      case Some(res: VideoRes) =>
        Some(res.linkDef)
      case _ => None
    }
  }

  def resolveFragment(id: String) = None
}

class NoteRenderer(val note: Note)
  extends Renderer(new ABMarkevenConf(note))

object env {
  def now = new Date

  def principal = currentUserOption

  def public(res: String) = {
    val f = new File(publicRoot, res.replaceFirst("^/", ""))
    if (f.exists && f.isFile)
      res + "?" + f.lastModified
    else res
  }
}

object DefaultUploader extends FileUploader

object db {

  object slave extends SimpleORMConfiguration("slave") {
    override def readOnly = true

    override lazy val url = cx.getAs[String]("db.slave.url")
      .getOrElse(throw new IllegalStateException("DB: Slave url must be defined."))
    override lazy val username = cx.getAs[String]("db.slave.username")
      .getOrElse(throw new IllegalStateException("DB: Slave username must be defined."))
    override lazy val password = cx.getAs[String]("db.slave.password")
      .getOrElse(throw new IllegalStateException("DB: Slave password must be defined."))
  }

  object master extends SimpleORMConfiguration("master") {

    override lazy val url = cx.getAs[String]("db.master.url")
      .getOrElse(throw new IllegalStateException("DB: Master url must be defined."))
    override lazy val username = cx.getAs[String]("db.master.username")
      .getOrElse(throw new IllegalStateException("DB: Master username must be defined."))
    override lazy val password = cx.getAs[String]("db.master.password")
      .getOrElse(throw new IllegalStateException("DB: Master password must be defined."))
  }

}