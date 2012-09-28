[#ftl]
<form action="/contacts/${contact.id}"
      class="submission partial"
      method="post">
  <h3>${msg['contact.edit.title']} «${contact.title}»</h3>
[#include "edit-base.p.ftl"/]
  <div class="submits centered">
    <input type="submit"
           value="${msg['contact.edit.submit']}"/>
    <span>${msg['or']}</span>
    <a href="javascript:;"
       class="hide-panel">${msg['cancel']}
    </a>
  </div>
</form>
