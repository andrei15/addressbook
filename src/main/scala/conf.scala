package net.whiteants

import _root_.freemarker.cache.FileTemplateLoader
import ru.circumflex._, freemarker._

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