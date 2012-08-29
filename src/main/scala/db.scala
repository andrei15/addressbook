package net.whiteants

import ru.circumflex._, ru.circumflex.core._, orm._
import java.util.Date


class User
  extends Record[Long, User]
  with IdentityGenerator[Long, User] {

  def PRIMARY_KEY = id
  def relation = User

  val id = "id".BIGINT.NOT_NULL.AUTO_INCREMENT
  val login = "login".TEXT.NOT_NULL
  val password = "password".TEXT.NOT_NULL
  val email ="email".TEXT.NOT_NULL

  val creationDate = "created_at".DATE.NOT_NULL(new Date)
}

object User
  extends User
  with Table[Long, User ] {

  val loginUnique = UNIQUE(login)

  validation
    .notEmpty(_.login)
    .notEmpty(_.password)
    .notEmpty(_.email)
    .unique(_.login)

  val u = User AS "u"

  def find(login: String, password: String): Option[User] =
    SELECT(u.*)
      .FROM(u)
      .add(u.login EQ login)
      .add(u.password EQ password)
      .unique()
}