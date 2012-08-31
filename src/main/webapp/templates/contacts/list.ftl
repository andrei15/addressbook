[#ftl]

[#assign user = env.principal]

[#assign main]
<div class="pad margin-top">
  <h2>${msg['contacts']}</h2>
  [#if contacts?size > 0]
    <table border bordercolor="peru" cellpadding="5" cellspacing ="0" bgcolor="#f5f5dc" >
      <tr>
        <th>${msg['contact.name']}</th>
        <th>${msg['contact.surname']}</th>
        <th>${msg['contact.phone']}</th>
        <th>${msg['contact.address']}</th>
        <th>${msg['contact.email']}</th>
        <th></th>
      </tr>
      [#list contacts as c]
        <tr>
          <td>${c.name}</td>
          <td>${c.surname}</td>
          <td>${c.phone}</td>
          <td>${c.address}</td>
          <td>${c.email}</td>
          <td>
            <a href="/contacts/${c.id}/~edit">${msg['addressbook.edit']}</a>
            <form method="post"
                  action="/contact/${c.id}">
              <input type="hidden" name="_method" value="delete"/>
              <input type="submit" value="${msg['addressbook.delete']}"/>
            </form>
          </td>
        </tr>
      [/#list]
    </table>
  [#else ]
    <p class="no-items">${msg['contacts.empty']}</p>
  [/#if]
  <div class="centered margin-top margin-bottom">
    <a href="/contacts/~new" class="btn">${msg['contacts.new']}</a>
  </div>
</div>
[/#assign]

[#include "layout.ftl"/]