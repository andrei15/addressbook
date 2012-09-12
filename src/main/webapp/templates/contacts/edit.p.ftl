[#ftl]

<div class="grid pad">
  <form action="/contacts/${contact.id}"
        id="editContact"
        class="submission"
        method="post">
    <h2>${msg['contact.edit.title']} ${contact.fullName}</h2>

  [#include "edit-base.ftl"/]

    <div class="submits centered">
      <input type="submit"
             value="${msg['contact.edit.submit']}"/>
      <span>${msg['or']}</span>
      <a href="/contacts">${msg['cancel']}</a>

    </div>
  </form>
  <div class="clear"></div>
