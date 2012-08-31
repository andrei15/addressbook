[#ftl]

[#assign user = env.principal]

[#assign main]
<div class="pad margin-top">
  <h2>${msg['contacts']}</h2>
  [#if contacts?size > 0]
    [#list contacts as c]
      <div class="contact">
        <img src="${c.gravatar("32")}" class="icon"/>
        <div class="title">${c.fullName}</div>
        <div class="subtle">
          <span>${c.phone}</span> &mdash;
          <a class="mail"
             href="mailto:${c.email}">${c.email}</a>
        </div>
        <div class="ctls">
          <a href="/contacts/${c.id}/~edit"
             title="${msg['contact.edit']}"
             class="btn">
            <img src="/img/pencil.png"/>
          </a>
          <a href="/contacts/${c.id}/~delete"
             title="${msg['contact.delete']}"
             class="btn inverse">
            <img src="/img/delete.png"/>
          </a>
        </div>
      </div>
    [/#list]
  [#--
  <table border bordercolor="peru" cellpadding="5" cellspacing ="0" bgcolor="#f5f5dc" >
    <tr>
      <th>${msg['contact.name']}</th>
      <th>${msg['contact.surname']}</th>
      <th>${msg['contact.phone']}</th>
      <th>${msg['contact.email']}</th>
      <th>${msg['contact.address']}</th>
      <th></th>
    </tr>
    [#list contacts as c]
      <tr>
        <td>${c.name}</td>
        <td>${c.surname}</td>
        <td>${c.phone}</td>
        <td>${c.email}</td>
        <td>${c.address}</td>
        <td>
          <a href="/contacts/${c.id}/~edit">${msg['contact.edit']}</a>
          <form method="post"
                action="/contact/${c.id}">
            <input type="hidden" name="_method" value="delete"/>
            <input type="submit" value="${msg['contact.delete']}"/>
          </form>
        </td>
      </tr>
    [/#list]
  </table>
  --]
  [#else ]
    <p class="no-items">${msg['contacts.empty']}</p>
  [/#if]
  <div class="centered margin-top margin-bottom">
    <a href="/contacts/~new" class="btn">${msg['contacts.new']}</a>
  </div>
</div>
[/#assign]

[#include "layout.ftl"/]