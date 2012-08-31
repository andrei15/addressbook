[#ftl]

[#assign user = currentUser]

[#assign content]
<h1>${msg['addressbook']}: ${user.login}</h1>
  [#if contacts?size > 0]

  <table border bordercolor="peru" cellpadding="5" cellspacing ="0" bgcolor="#f5f5dc" >

    <tr>
      <th>${msg['name']}</th>
      <th>${msg['surname']}</th>
      <th>${msg['phone']}</th>
      <th>${msg['address']}</th>
      <th>${msg['email']}</th>
      <th>${msg['commands']}</th>

    </tr>
    [#list contacts as c]
      <tr>
        <td>${c.name}</td>
        <td>${c.surname}</td>
        <td>${c.phone}</td>
        <td>${c.address}</td>
        <td>${c.email}</td>
        <td>
          <a href="/addressbook/${currentUser.id}/contact/${c.id}/~edit">${msg['addressbook.edit']}</a>
          <form method="post"
                action="/addressbook/${currentUser.id}/contact/${c.id}">
            <input type="hidden" name="_method" value="delete"/>
            <input type="submit" value="${msg['addressbook.delete']}"/>
          </form>
        </td>
      </tr>
    [/#list]
  </table>
  [#else ]
  <p>${msg['contacts.empty']}</p>
  [/#if]
<p></p>
<a href="/addressbook/${currentUser.id}/~new">${msg['addressbook.create']}</a>
[#--[#if currentUser??]--]
[#--<a href="/addressbook/${currentUser.id}">${currentUser.login}</a>--]
[#--[#else]--]
[#--<a href="/auth/login">${msg['login']}</a>--]
[#--[/#if]--]
[/#assign]

[#include "layout.ftl"/]