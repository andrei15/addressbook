package net.whiteants

import ru.circumflex._, core._, freemarker._
import markeven.DefaultSanitizer

class ABSanitizer extends DefaultSanitizer {
  whitelist.addTags("iframe")
  whitelist.addAttributes("iframe", "class", "type", "width", "height", "src", "frameborder")
}