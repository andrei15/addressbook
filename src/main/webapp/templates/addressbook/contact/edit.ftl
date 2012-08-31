[#ftl]

[#assign error = flash["error"]!/]

[#assign content]
  [#if error??]<p>${error}</p>[/#if]
<form action="/addressbook/${currentUser.id}/contact/${record.id}/~edit" method="post">
  <dl>
    <dt><label for="n">${msg['name']}</label></dt>
    <dd>
      <input id="n"
             name="n"
             value="${record.name!?html}"
             size="25"
             type="text"/>
    </dd>
    <dt><label for="s">${msg['surname']}</label></dt>
    <dd>
      <input id="s"
             name="s"
             value="${record.surname!?html}"
             size="25"
             type="text"/>
    </dd>
    <dt><label for="p">${msg['phone']}</label></dt>
    <dd>
      <input id="p"
             name="p"
             value="${record.phone!?html}"
             size="25"
             type="text"/>
    </dd>
    <dt><label for=a">${msg['address']}</label></dt>
    <dd>
      <input id="a"
             name="a"
             value="${record.address!?html}"
             size="25"
             type="text"/>
    </dd>
    <dt><label for=e">${msg['email']}</label></dt>
    <dd>
      <input id="e"
             name="e"
             value="${record.email!?html}"
             size="25"
             type="text"/>
    </dd>

    <input type="submit"
           value="${msg['addressbook.edit']}"
  </dl>
</form>
[/#assign]
[#include "layout.ftl"/]