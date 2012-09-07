package net.whiteants

import ru.circumflex._,  web._, freemarker._

object notice  {

  var msg=Map[String, String]()
  var errors=Map[String, String]()

  def addMsg (key : String,value : String)  =
  {
    msg+=(key, value)
  }

  def addError (key : String,value : String)  =
  {
    errors+=(key, value)
  }

}