[#ftl]

[#assign content]

  [#assign errors = flash['errors']![]/]

  [#if errors?size > 0]
  <ul>
    [#list errors as e]
      <li>${e}</li>
    [/#list]
  </ul>
  [/#if]

<form action="/auth/signup"
      method="post">
  <dl>
    <dt><label for="l">${msg['user.login']}</label></dt>
    <dd>
      <input id="l"
             name="l"
             size="25"
             type="text"
             placeholder="${msg['name.placeholder']}"/>
    </dd>
    <dt><label for="p">${msg['user.password']}</label></dt>
    <dd>
      <input id="p"
             name="p"
             size="25"
             type="password"
             placeholder="${msg['user.password']}"/>
    </dd>
    <dt><label for="l">${msg['name.email']}</label></dt>
    <dd>
      <input id="e"
             name="e"
             size="25"
             type="email"
             placeholder="${msg['email.placeholder']}"/>
    </dd>
    <input type="submit"
           value="${msg['create']}"/>
    <span>${msg['or']}</span>
    <a href="/auth/login">${msg['login']}</a>
  </dl>
</form>
[/#assign]

[#include "layout.ftl"/]