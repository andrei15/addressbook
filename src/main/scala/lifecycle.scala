package net.whiteants

import ru.circumflex._, core._, web._
import javax.servlet.http.{HttpSessionEvent, HttpSessionListener}
import org.apache.commons.io.FileUtils
import java.io.File

class SessionUploadsFilesCleaner extends HttpSessionListener {
  def sessionDestroyed(se: HttpSessionEvent) {
    FileUtils.deleteQuietly(new File(uploadsDir, se.getSession.getId))
  }

  def sessionCreated(se: HttpSessionEvent) {}
}