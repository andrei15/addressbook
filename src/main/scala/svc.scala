package net.whiteants

import ru.circumflex._, core._, web._, orm._, freemarker._

class SvcRouter extends Router {

  if (currentUserOption.isEmpty) sendError(404)

  get("/~upload") = {
    'uuid := randomUUID
    ftl("/svc/upload/index.p.ftl")
  }

  post("/~upload") = {
    try {
      val fd = DefaultUploader.handleUpload()
      'fileDescription := fd
      'message := msg.fmt("upload.file.success", "title" -> fd.originalFileName)
    } catch {
      case e: ValidationException =>
        Notice.addErrors(e.errors)
    }
    ftl("/svc/upload/done.ftl")
  }

  sub("/admin") = {

    get("/?") = ftl("/svc/admin/admin.ftl")

    post("/query") = {
      val p = expr[String]("Contact.name")
      val query = param("q").trim.toSql(p)
      'res := query.list.mkString("\n")
      sendJSON("/svc/admin/query.ftl")
    }
  }
}