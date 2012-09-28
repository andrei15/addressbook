[#ftl]

<form class="submission pad"
      action="/contacts/${contact.id}/notes/${note.uuid}/file/${file.uuid}/">
  <h2>${msg['notes.file.delete']}</h2>
  <div class="pad">
  [#include "/locale/notes.file.delete.ftl"/]
  </div>
  <input type="hidden" name="_method" value="delete"/>
  <div class="submits centered">
    <input type="submit"
           class="btn inverse"
           value="${msg['contact.delete.submit']}">
    <span>${msg['or']}</span>
    <a href="javascript:;"
       class="close">
    ${msg['cancel']}
    </a>
  </div>
</form>