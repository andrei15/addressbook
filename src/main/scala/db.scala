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
  val email = "email".TEXT.NOT_NULL

  val creationDate = "created_at".DATE.NOT_NULL(new Date)
}

class AddressBook
  extends Record[Long, AddressBook]
  with IdentityGenerator[Long, AddressBook] {

  def PRIMARY_KEY = id
  def relation = AddressBook

  val id = "id".BIGINT.NOT_NULL.AUTO_INCREMENT
  val owner = "owner".BIGINT.NOT_NULL.REFERENCES(User).ON_DELETE(CASCADE)
  val name = "name".TEXT.NOT_NULL
  val surname = "surname".TEXT.NOT_NULL
  val phone = "phone".TEXT.NOT_NULL
  val address = "address".TEXT.NOT_NULL
  val email = "email".TEXT.NOT_NULL
}

object AddressBook
  extends AddressBook
  with Table[Long, AddressBook] {

  val ab = AddressBook AS "ab"

  def findAll(user: User): Seq[AddressBook] =
    SELECT(ab.*)
      .FROM(ab)
      .WHERE(ab.owner IS user)
      .list()

}

object User
  extends User
  with Table[Long, User] {

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