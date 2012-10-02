package net.whiteants

import ru.circumflex._, core._, web._, freemarker._

class ProfileRouter extends Router {

  requireAuth()

  partial.addRecovers(() => currentUser.refresh())

  get("/?") = ftl("/profile/view.ftl")

  post("/?") = partial {
    if (currentUser.password() == sha256(param("p"))) {
      currentUser.login := param("n")
      currentUser.email := param("e")
      currentUser.save()
      auth.setCookie(currentUser)
      'redirect := "/profile"
      Notice.addInfo("saved")
    } else Notice.addInfo("user.password.error")
  }

  get("/~edit-password").and(request.body.isXHR) = ftl("/profile/edit-password.p.ftl")

  post("/~edit-password") = partial {
    val oldPassword = sha256(param("op").trim)
    val newPassword = param("np").trim
    val confirmPassword = param("cp").trim
    if ((currentUser.password() == oldPassword) && (newPassword == confirmPassword) && !newPassword.isEmpty) {
      currentUser.password := User.getSha256Password(newPassword)
      currentUser.save()
      auth.setCookie(currentUser)
      'redirect := "/profile"
      Notice.addInfo("saved")
    } else Notice.addInfo("user.password.error")
  }
}