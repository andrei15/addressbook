[#ftl]

[#assign __base = "/contacts/${contact.id}/notes/${note.uuid}"/]

<form class="submission pad"
      action="${__base}">
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
    <a href="${__base}">${msg['cancel']}</a>
  </div>
</form>