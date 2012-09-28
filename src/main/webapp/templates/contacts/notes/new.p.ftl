[#ftl]

[#assign main]

<div class="grid">
  <div class="w50">
    <h2>${msg['contact.note.new']}</h2>
    <form id="newNote"
          action="/contacts/${contact.id}/notes"
          class="submission"
          method="post"
          enctype="multipart/form-data">
      [#include "edit-base.ftl"/]
      <div class="submits centered">
        <input type="submit"
               value="${msg['notes.new']}"/>
        <span>${msg['or']}</span>
        <a href="/contacts/${contact.id}/notes">${msg['cancel']}</a>
      </div>
    </form>

  </div>
  <div class="w50 no-mobiles">
    <div class="centered">
      <img src="/img/icons/128/note.png"/>
    </div>
  </div>
</div>

[/#assign]

[#include "layout.ftl"/]