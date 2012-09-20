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

  def getCookie(ip: String) = login() + ":" + sha256(ip + password())

  def getSha256Password(password: String) = sha256(password)
}

object User
  extends User
  with Table[Long, User] {

  val loginUnique = UNIQUE(login)
  val emailUnique = UNIQUE(email)

  validation
    .unique(_.login)
    .notEmpty(_.login)
    .notEmpty(_.password)
    .unique(_.email)
    .pattern(_.email, Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$"), "syntax")

  val u = User AS "u"

  def find(login: String, password: String): Option[User] =
    SELECT(u.*)
      .FROM(u)
      .add(u.login EQ login)
      .add(u.password EQ sha256(password))
      .unique()

  def findLogin(login: String): Option[User] =
    SELECT(u.*)
      .FROM(u)
      .add(u.login EQ login)
      .unique()
}

class Contacts
  extends Record[Long, Contacts]
  with IdentityGenerator[Long, Contacts] {

  def PRIMARY_KEY = id
  def relation = Contacts

  val id = "id".BIGINT.NOT_NULL.AUTO_INCREMENT
  val owner = "owner".BIGINT.NOT_NULL.REFERENCES(User).ON_DELETE(CASCADE)
  val name = "name".TEXT.NOT_NULL
  val surname = "surname".TEXT.NOT_NULL
  val phone = "phone".TEXT.NOT_NULL
  val email = "email".TEXT.NOT_NULL
  val address = "address".TEXT.NOT_NULL("")
  val _notes = "notes".HTML.NOT_NULL("<notes/>")

  lazy val notes = (new Notes(this)).loadString(_notes())

  def fullName = surname() + " " + name()

  def gravatar(size: String) = "http://www.gravatar.com/avatar/" + md5(email()) +
    "?d=identicon&amp;" + "size=" + size
}

object Contacts
  extends Contacts
  with Table[Long, Contacts] {

  validation
    .notEmpty(_.name)
    .notEmpty(_.surname)
    .pattern(_.email, Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$"), "syntax")
    .pattern(_.phone, Pattern.compile("^(8|\\+[0-9]{1,4}) *-? *[\\(]?[0-9]{3,6}[\\)]?( *-? *[0-9]){5,}$"), "syntax")

  val ab = Contacts AS "ab"

  def findAll(user: User): Seq[Contacts] =
    SELECT(ab.*)
      .FROM(ab)
      .WHERE(ab.owner IS user)
      .list()

  def userSearch(user: User, param: String): Seq[Contacts] = {
    val paramList = param.split(" ").map(_.trim).filter(_ != "")
    val p = AND()
    paramList.foreach { param =>
      val prm = param + "%"
      val or = OR(ab.name LLIKE prm, ab.surname LLIKE prm,
        ab.email LLIKE prm, ab.phone LLIKE prm,
        ab.address LLIKE prm)
      p.add(or)
    }
    SELECT(ab.*)
      .FROM(ab)
      .add(ab.owner IS user)
      .add(p)
      .list()
  }

  def fetch(id: String) = {
    try {
      Contacts.get(id.toLong).getOrElse(sendError(404))
    } catch {
      case e: Exception => sendError(404)
    }
  }
}