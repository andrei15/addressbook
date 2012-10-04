package net.whiteants

import _root_.freemarker.template._
import ru.circumflex._, freemarker._

import java.io.File
import java.util.Date

class FtlConfiguration extends DefaultConfiguration {
  setObjectWrapper(new ScalaObjectWrapper())
  setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER)
  setNumberFormat("0.##")
  setSharedVariable("env", env)
  setSharedVariable("me", MarkevenDirective)
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