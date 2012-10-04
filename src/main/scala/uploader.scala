package net.whiteants

import ru.circumflex._, core._, web._
import org.apache.commons.fileupload.disk.DiskFileItemFactory
import org.apache.commons.fileupload.FileItem
import java.io.File

class FileUploader {

  val maxSize = cx.getAs[Long]("upload.maxSize").getOrElse(10240 * 1024l)

  def handleUpload() = {
    if (!request.body.isMultipart) sendError(404)
    request.body.parseFileItems(new DiskFileItemFactory()).foreach { fi =>
      if (!fi.isFormField)
        ctx.update(fi.getFieldName, fi)
    }
    val fi = ctx.getAs[FileItem]("file")
      .getOrElse(throw new ValidationException("upload.fileNotSpecified"))
    val uploadFile = fi.getName
    if (fi.getSize > maxSize)
      throw new ValidationException("upload.maxSize")
    val file = new FileDescription
    file._uuid := randomUUID
    val i = uploadFile.lastIndexOf(".")
    if (i != -1) {
      val ext = uploadFile.substring(i + 1)
      file._originalName := uploadFile.substring(0, i)
      file._ext := ext
      try {
        val f = new File(uploadsDir, request.session.id.getOrElse(""))
        f.mkdirs()
        fi.write(new File(f, file.fileName))
      } catch {
        case e: Exception =>
          sendError(500)
      }
    }
    file
  }
}