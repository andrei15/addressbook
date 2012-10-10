package net.whiteants

import _root_.freemarker.cache.FileTemplateLoader
import ru.circumflex._, core._, freemarker._, orm._

import java.io.File
import java.util.Date

class FtlConfiguration extends DefaultConfiguration {
  setNumberFormat("0.##")
  setSharedVariable("env", env)
  setSharedVariable("me", MarkevenDirective)
  addLoader(new FileTemplateLoader(new File("src/main/webapp/templates")))
  _root_.freemarker.log.Logger.selectLoggerLibrary(_root_.freemarker.log.Logger.LIBRARY_SLF4J)
}

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