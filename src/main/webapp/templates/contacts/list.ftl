[#ftl]

[#assign user = env.principal]

[#assign main]
<div class="pad">
  [#include "search-form.p.ftl"/]
  <h2>${msg['contacts']}</h2>
  <div class="right-align">
    <a href="/contacts/~new"
       class="btn">
    ${msg['contacts.new']}
    </a>
  </div>
  <div class="grid margin-top">
    <div class="w100">
      [#if contacts?size > 0]
        [#list contacts as c]
          <div class="pill">
            <img src="${c.gravatar("32")}"
                 class="icon"/>
            <div class="title">${c.title}</div>
            <div class="subtle">
              <span>${c.phone}</span> &mdash;
              <a href="mailto:${c.email}">${c.email}</a>
            </div>
            <div class="ctls">
              <a href="/contacts/${c.id}/notes/"
                 class="btn"
                 title="${msg['contact.notes.view']}">
                <img src="/img/icons/48/note_book.png"/>
              </a>
              <a href="/contacts/${c.id}"
                 class="btn"
                 title="${msg['contact.view']}">
                <img src="/img/icons/48/search_business_user.png"/>
              </a>
              <a href="/contacts/${c.id}/~edit"
                 class="btn edit-panel"
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
    </div>
    <div id="edit-container"
         class="w50 hidden"></div>
  </div>
</div>
[/#assign]

[#include "layout.ftl"/]