package net.whiteants

import ru.circumflex._, ru.circumflex.core._, orm._, web._
import java.util.Date
import java.util.regex.Pattern

trait BaseRecord[R <: BaseRecord[R]]
  extends Record[Long, R]
  with IdentityGenerator[Long, R] {this: R =>

  def PRIMARY_KEY = id

  val id = "id".BIGINT.NOT_NULL.AUTO_INCREMENT
}

trait BaseTable[R <: BaseRecord[R]]
  extends BaseRecord[R]
  with Table[Long, R] {this: R =>

  def fetch(id: String): R = fetchOption(id).getOrElse(sendError(404))

  def fetchOption(id: String): Option[R] = try {
    get(id.trim.toLong)
  } catch {
    case e: NumberFormatException => None
  }
}

class User
  extends BaseRecord[User] {

  def relation = User

  val login = "login".TEXT.NOT_NULL.addSetter(_.toLowerCase)
  val password = "password".TEXT.NOT_NULL
  val email = "email".TEXT.NOT_NULL

  val creationDate = "created_at".DATE.NOT_NULL(new Date)

  def gravatar(size: String) = "http://www.gravatar.com/avatar/" + md5(email()) +
    "?d=identicon&amp;" + "size=" + size

  def getSha256Password(password: String) = sha256(password)
}

object User
  extends User
  with BaseTable[User] {

  val loginUnique = UNIQUE(login)
  val emailUnique = UNIQUE(email)

  validation
    .unique(_.login)
    .notEmpty(_.login)
    .notEmpty(_.password)
    .unique(_.email)
    .pattern(_.email, Regex.emailPattern, "syntax")

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

class Contact
  extends BaseRecord[Contact] {

  def relation = Contact

  val owner = "owner".BIGINT.NOT_NULL.REFERENCES(User).ON_DELETE(CASCADE)
  val name = "name".TEXT.NOT_NULL
  val surname = "surname".TEXT.NOT_NULL
  val phone = "phone".TEXT.NOT_NULL
  val email = "email".TEXT.NOT_NULL
  val address = "address".TEXT.NOT_NULL("")
  val _notes = "notes".TEXT.NOT_NULL("<notes/>")

  lazy val notes = (new Notes(this)).loadString(_notes())

  def title = surname() + " " + name()

  def gravatar(size: String) = "http://www.gravatar.com/avatar/" + md5(email()) +
    "?d=identicon&amp;" + "size=" + size
}

object Contact
  extends Contact
  with BaseTable[Contact] {

  validation
    .notEmpty(_.name)
    .notEmpty(_.surname)
    .pattern(_.email, Regex.emailPattern, "syntax")
    .pattern(_.phone, Regex.phonePattern, "syntax")

  val ab = Contact AS "ab"

  def findAll(user: User): Seq[Contact] =
    SELECT(ab.*)
      .FROM(ab)
      .WHERE(ab.owner IS user)
      .ORDER_BY(ab.surname)
      .list()

  def userSearch(user: User, param: String): Seq[Contact] = {
    val paramList = param.split(" ").map(_.trim).filter(_ != "")
    val p = AND()
    paramList.foreach { param =>
      val prm = "%" + param + "%"
      val or = OR()
        .add(ab.name LLIKE prm)
        .add(ab.surname LLIKE prm)
        .add(ab.email LLIKE prm)
        .add(ab.phone LLIKE prm)
        .add(ab.address LLIKE prm)
      p.add(or)
    }
    SELECT(ab.*)
      .FROM(ab)
      .add(ab.owner IS user)
      .add(p)
      .ORDER_BY(ab.surname)
      .list()
  }
}