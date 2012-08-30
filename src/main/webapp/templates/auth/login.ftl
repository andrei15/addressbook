[#ftl]

[#assign error = flash["error"]!/]

[#assign content]
  [#if error??]<p>${error}</p>[/#if]
<h2>${msg['addressbook']}</h2>
<form action="/auth/login"
      method="post">
  <dl>
    <dt><label for="l">${msg['user.login']}</label></dt>
    <dd>
      <input id="l"
             name="l"
             size="25"
             type="text"/>
    </dd>
    <dt><label for="p">${msg['user.password']}</label></dt>
    <dd>
      <input id="p"
             name="p"
             size="25"
             type="password"/>
    </dd>
    <input type="submit"
           value="${msg['login']}"/>
    <span>${msg['or']}</span>
    <a href="/auth/signup">${msg['signup']}</a>
  </dl>
</form>
[/#assign]

[#include "layout.ftl"/]