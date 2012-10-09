[#ftl]

[#assign main]
<div class="grid pad">
  <div class="w100">
    <h2>${msg['contact.note.edit']}</h2>
    <form action="/contacts/${contact.id}/notes/${note.uuid}"
          class="submission partial"
          method="post">
      [#include "edit-base.p.ftl"/]
      <div class="submits centered margin-top">
        <input type="submit"
               value="${msg['notes.edit']}"/>
        <span>${msg['or']}</span>
        <a href="/contacts/${contact.id}/notes/${note.uuid}">${msg['cancel']}</a>
      </div>
    </form>
  </div>
</div>
[/#assign]

[#include "layout.ftl"/]