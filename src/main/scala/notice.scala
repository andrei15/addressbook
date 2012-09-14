package net.whiteants

import ru.circumflex._, core._,  web._, freemarker._
import collection.mutable.ListBuffer

object Notice  {


  def notices = flash.getAs[ListBuffer[Notice]]("notices") match {
    case Some(l: ListBuffer[Notice]) =>
      flash.update("notices", l)
      l
    case _ =>
      val l = new ListBuffer[Notice]()
      flash.update("notices", l)
      l
  }

  def addInfo(message: String) {
    notices.append(new Notice("info", msg.get(message).getOrElse(message)))
  }
  def addError(message: String) {
    notices.append(new Notice("error", msg.get(message).getOrElse(message)))
  }
  def addErrors(err: Seq[Msg]) {
    err.foreach { e =>
      notices.append(new Notice("error", e.toString()))
    }
  }
}

class Notice(val kind: String, val message: String)