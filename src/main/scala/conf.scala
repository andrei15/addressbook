package net.whiteants

import _root_.freemarker.template._
import ru.circumflex._,  freemarker._

import java.util.Date
import net.whiteants


class FtlConfiguration extends DefaultConfiguration {
  setObjectWrapper(new ScalaObjectWrapper())
  setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER)
  setNumberFormat("0.##")
  setSharedVariable("env", env)
}

object env {
  def now = new Date
  def principal = whiteants.currentUserOption
}