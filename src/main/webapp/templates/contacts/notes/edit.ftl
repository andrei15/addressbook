[#ftl]

[#assign main]
<div class="grid pad">
  <div class="w50">
    <h2>${msg['contact.note.edit']}</h2>
    <form id ="editNote"
          action="/contacts/${contact.id}/notes/${note.uuid}"
          class="submission"
          method="post"
          enctype="multipart/form-data">
      [#include "edit-base.p.ftl"/]
      <div class="submits centered">
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