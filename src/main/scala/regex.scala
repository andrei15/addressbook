package net.whiteants

import java.util.Random
import java.util.regex.Pattern

object Regex {
  val youtubePattern =
    Pattern.compile("^(?i:(?:https?://)?[a-z0-9_.-]*?\\.?youtu(?:\\.be|be\\.com))/(?:.*v(?:/|=)|(?:.*/)?)" +
      "([a-zA-Z0-9-_]+)")
  val emailPattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$")
  val phonePattern = Pattern.compile("^(8|\\+[0-9]{1,4}) *-? *[\\(]?[0-9]{3,6}[\\)]?( *-? *[0-9]){5,}$")
}