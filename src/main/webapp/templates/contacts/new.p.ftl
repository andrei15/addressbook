[#ftl]

[#assign contact = {}/]
[#assign main]

<div class="grid pad">
  <div class="w33">
    <h2>${msg['contact.new.title']}</h2>
    <form action="/contacts"
          class="submission partial"
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
[/#assign]

[#include "layout.ftl"/]