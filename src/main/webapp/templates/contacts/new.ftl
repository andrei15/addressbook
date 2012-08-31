[#ftl]

[#assign errors = flash["errors"]![]/]
[#assign contact = {}/]

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
    <h2>${msg['contact.new.title']}</h2>
    <form action="/contacts"
          class="submission"
          method="post">
      [#include "edit-base.ftl"/]
      <div class="submits centered">
        <input type="submit"
               value="${msg['contact.new']}"/>
        <span>${msg['or']}</span>
        <a href="/contacts">${msg['cancel']}</a>
      </div>
    </form>
  </div>
  <div class="w66 no-mobiles">
    <div class="centered">
      <img src="/img/new-contact.png"/>
    </div>
  </div>
</div>
<div class="clear"></div>
[/#assign]

[#include "layout.ftl"/]