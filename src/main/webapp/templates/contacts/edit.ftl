[#ftl]

[#assign errors = flash["errors"]![]/]

[#assign main]
  [#if errors?size > 0]
  <ul>
    [#list errors as e]
      <li>${e}</li>
    [/#list]
  </ul>
  [/#if]
<div class="grid pad">
  <div class="w33">
    <form action="/contacts/${contact.id}"
          class="submission"
          method="post">
      <h2>${msg['contact.edit.title']}</h2>

      [#include "edit-base.ftl"/]

      <div class="submits centered">
        <input type="submit"
               value="${msg['contact.edit.submit']}"/>
        <span>${msg['or']}</span>
        <a href="/contacts">${msg['cancel']}</a>

      </div>
    </form>
  </div>
  <div class="w66 no-mobiles">
    <div class="centered">
      <img src="/img/edit-contact.png"/>
    </div>
  </div>
</div>
<div class="clear"></div>


[/#assign]
[#include "layout.ftl"/]