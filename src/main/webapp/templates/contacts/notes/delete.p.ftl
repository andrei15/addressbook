[#ftl]

<form class="submission pad"
      action="/contacts/${contact.id}/notes/${note.uuid}">
  <h2>${msg['notes.delete.title']}</h2>
  <div class="pad">
  [#include "/locale/notes.delete.ftl"/]
  </div>
  <input type="hidden" name="_method" value="delete"/>
  <div class="submits centered">
    <input type="submit"
           class="btn inverse"
           value="${msg['contact.delete.submit']}">
    <span>${msg['or']}</span>
    <a href="/contacts/${contact.id}/notes/">${msg['cancel']}</a>
  </div>
</form>