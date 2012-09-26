[#ftl]

[#assign user = env.principal]

[#assign main]
<div class="pad ">
  <div class="grid">
    <div class="w50 hide50">
      [#include "search-form.p.ftl"/]
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
              <a href="/contacts/${c.id}/notes/"
                 class="btn note"
                 title="${msg['contact.notes.view']}">
                <img src="/img/icons/48/note_book.png"/>
              </a>
              <a href="/contacts/${c.id}"
                 class="btn view"
                 title="${msg['contact.view']}">
                <img src="/img/icons/48/search_business_user.png"/>
              </a>
              <a href="/contacts/${c.id}/~edit"
                 class="btn editpanel"
                 data-container="#edit-container"
                 title="${msg['contact.edit']}">
                <img src="/img/pencil.png"/>
              </a>
              <a href="/contacts/${c.id}/~delete"
                 title="${msg['contact.delete']}"
                 rel="popup"
                 class="btn inverse">
                <img src="/img/delete.png"/>
              </a>
            </div>
          </div>
        [/#list]
      [#else ]
        <p class="no-items">${msg['contacts.empty']}</p>
      [/#if]
      <div class="centered margin-top margin-bottom clear">
        <a href="/contacts/~new" class="btn">${msg['contacts.new']}</a>
      </div>
    </div>
    <div id="edit-container" class="w50"></div>
  </div>

</div>
[/#assign]

[#include "layout.ftl"/]