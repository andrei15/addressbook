[#ftl]

[#assign __base = "/contacts/${contact.id}/notes/${note.uuid}"/]

<form action="${__base}/resources/img"
      class="submission partial"
      method="post">
[#include "../edit-base.p.ftl"/]
  <div class="submits centered">
    <input type="submit"
           value="${msg['contact.edit.submit']}"/>
    <span>${msg['or']}</span>
    <a href="javascript:;"
       class="hide-panel">${msg['cancel']}
    </a>
  </div>
</form>