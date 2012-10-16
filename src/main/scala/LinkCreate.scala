package net.whiteants

import ru.circumflex._, core._, freemarker._, orm._
import markeven.LinkDef

class LinkCreate(uuid: String, note: Note) {

  def create(): LinkDef = {
    val href = new LinkDef("/contacts/" + note.notes.contact.id() + "/notes/" + uuid)
    href
  }
}

class ImgLinkCreate(uuid: String) {

  def create(): LinkDef = {
    val href = new LinkDef(uuid)
    href
  }
}

class VideoLinkCreate(uuid: String) {

  def create(): LinkDef = {
    val href = new LinkDef(uuid)
    href
  }
}