package net.whiteants

import ru.circumflex._, core._, web._, freemarker._

class AddressBookRouter extends Router {

  sub("/:id") = {
    if (currentUserOption.isEmpty)
      sendError(404)

    if (currentUser.id().toString != param("id"))
      sendError(404)

    get("/?") = {
      'contacts := AddressBook.findAll(currentUser)
      ftl("/addressbook/view.ftl")
    }
    get("/~new") = ftl("/addressbook/new.ftl")

    post("/") = {
      val addressBook = new AddressBook
      addressBook.owner := currentUser
      addressBook.name := param("n")
      addressBook.surname:=param("s")
      addressBook.phone:=param("p")
      addressBook.address:=param("a")
      addressBook.email:=param("e")
      try {
        addressBook.save()
      } catch {
        case e: ValidationException =>
          flash.update("errors", e.errors)
          sendRedirect(prefix + "/~new")
      }
      sendRedirect(prefix)
    }

    get("/contact/:recid/~edit")=
      {
        AddressBook.get(param("recid").toLong)   match {
          case Some(record) =>
            'record:=record

            ftl("/addressbook/contact/edit.ftl")
          case _ => sendError(404)
        }
        //ftl("/addressbook/contact/edit.ftl")
      }

    post("/contact/:recid/~edit") = {
        AddressBook.get(param("recid").toLong) match {
          case Some(record) =>
            //record.owner := currentUser
            record.name := param("n")
            record.surname:=param("s")
            record.phone:=param("p")
            record.address:=param("a")
            record.email:=param("e")
            try {
              record.save()
            } catch {
              case e: ValidationException =>
                flash.update("errors", e.errors)
                sendRedirect(prefix + "/~edit")
            }
            sendRedirect(prefix)
          case _ => sendError(404)
        }
      }
    delete("/contact/:recid") = {
        AddressBook.get(param("recid").toLong) match {
          case Some(record) =>
            try  {
              record.DELETE_!()
              flash("message") = "Record deleted"
            } catch {
              case e:
                ValidationException =>
                flash.update("errors", e.errors)
                sendError(404)
            }
            redirect(prefix)
          case _ => sendError(404)
          // ftl("/addressbook/contact/edit.ftl")
        }
      }

  }
}