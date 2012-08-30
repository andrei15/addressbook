[#ftl]

[#assign error = flash["error"]!/]

[#assign content]
  [#if error??]<p>${error}</p>[/#if]
<h2>${msg['addressbook.create']} </h2>
<form action="/addressbook/${currentUser.id}/"
      method="post">
  <dl>
    <dt><label for="n">${msg['name']}</label></dt>
    <dd>
      <input id="n"
             name="n"
             size="25"
             type="text"/>
    </dd>
    <dt><label for="s">${msg['surname']}</label></dt>
    <dd>
      <input id="s"
             name="s"
             size="25"
             type="text"/>
    </dd>
    <dt><label for="p">${msg['phone']}</label></dt>
    <dd>
      <input id="p"
             name="p"
             size="25"
             type="text"/>
    </dd>
    <dt><label for=a">${msg['address']}</label></dt>
    <dd>
      <input id="a"
             name="a"
             size="25"
             type="text"/>
    </dd>
    <dt><label for=e">${msg['email']}</label></dt>
    <dd>
      <input id="e"
             name="e"
             size="25"
             type="text"/>
    </dd>

    <input type="submit"
           value="${msg['addressbook.create']}"
  </dl>
</form>
[/#assign]

[#include "layout.ftl"/]