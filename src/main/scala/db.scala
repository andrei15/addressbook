package net.whiteants

import ru.circumflex._, ru.circumflex.core._, orm._, web._
import java.util.Date
import java.util.regex.Pattern


class User
  extends Record[Long, User]
  with IdentityGenerator[Long, User] {

  def PRIMARY_KEY = id

  def relation = User

  val id = "id".BIGINT.NOT_NULL.AUTO_INCREMENT
  val login = "login".TEXT.NOT_NULL.addSetter(_.toLowerCase)
  val password = "password".TEXT.NOT_NULL
  val email = "email".TEXT.NOT_NULL

  val creationDate = "created_at".DATE.NOT_NULL(new Date)

  def gravatar(size: String) = "http://www.gravatar.com/avatar/" + md5(email()) +
    "?d=identicon&amp;" + "size=" + size

}

object User
  extends User
  with Table[Long, User] {

  val loginUnique = UNIQUE(login)
  val emailUnique = UNIQUE(email)

  validation
    .notEmpty(_.login)
    .notEmpty(_.password)
    .unique(_.login)
    .unique(_.email)
    .pattern(_.email, Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$"), "syntax")

  val u = User AS "u"

  def find(login: String, password: String): Option[User] =
    SELECT(u.*)
      .FROM(u)
      .add(u.login EQ login)
      .add(u.password EQ password)
      .unique()
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
  val email = "email".TEXT.NOT_NULL
  val address = "address".TEXT.NOT_NULL("")

  def fullName = surname() + " " + name()

  def gravatar(size: String) = "http://www.gravatar.com/avatar/" + md5(email()) +
    "?d=identicon&amp;" + "size=" + size
}

object AddressBook
  extends AddressBook
  with Table[Long, AddressBook] {

  validation
    .notEmpty(_.name)
    .notEmpty(_.surname)
    .pattern(_.email, Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$"), "syntax")
    .pattern(_.phone, Pattern.compile("^(8|\\+[0-9]{1,4}) *-? *[\\(]?[0-9]{3,6}[\\)]?( *-? *[0-9]){5,}$"), "syntax")

  val ab = AddressBook AS "ab"

  def findAll(user: User): Seq[AddressBook] =
    SELECT(ab.*)
      .FROM(ab)
      .WHERE(ab.owner IS user)
      .list()

  def fetch(id: String) = {
    try {
      AddressBook.get(id.toLong).getOrElse(sendError(404))
    } catch {
      case e: Exception => sendError(404)
    }
  }
}