[#ftl]

[#assign note = {} /]

[#assign main]
<div class="grid">
  <div class="w100">
    <h2>${msg['contact.note.new']}</h2>
    <form action="/contacts/${contact.id}/notes"
          class="submission partial"
          method="post">
      [#include "edit-base.p.ftl"/]
      <div class="submits centered margin-top">
        <input type="submit"
               value="${msg['notes.new']}"/>
        <span>${msg['or']}</span>
        <a href="/contacts/${contact.id}/notes">${msg['cancel']}</a>
      </div>
    </form>
  </div>
</div>

[/#assign]

[#include "layout.ftl"/]