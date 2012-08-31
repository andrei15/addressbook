[#ftl]

[#assign main]
<form class="submission"
      action="/contacts/${contact.id}">
  <h2>${msg['contact.delete.title']}</h2>
  <div class="pad">
    [#include "/locale/contact.delete.ftl"/]
  </div>
  <input type="hidden" name="_method" value="delete"/>
  <div class="submits centered">
    <input type="submit"
           class="btn inverse"
           value="${msg['contact.delete.submit']}">
    <span>${msg['or']}</span>
    <a href="/contacts">${msg['cancel']}</a>
  </div>
</form>
[/#assign]

[#include "layout.ftl"/]