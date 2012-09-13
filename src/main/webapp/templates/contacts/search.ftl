[#ftl]

[#assign user = env.principal]

[#assign main]
<div class="pad margin-top">

  <div class="grid pad">

    <div class="w66">
      <h2>${msg['contact.search']}</h2>
      [#if search?size > 0]
        [#list search as s]
          <div class="contact">
            <img src="${s.gravatar("32")}" class="icon"/>
            <div class="title">${s.fullName}</div>
            <div class="subtle">
              <span>${s.phone}</span> &mdash;
              <a class="mail"
                 href="mailto:${s.email}">${s.email}</a>
            </div>
            <div class="ctls">
              <a href="/contacts/${s.id}"
                 title="${msg['contact.view']}">
                <img src="/img/icons/48/search_business_user.png"/>
              </a>
              <a href="/contacts/${s.id}/~edit"
                 class="btn edform"
                 data-container="#edit-container"
                 title="${msg['contact.edit']}">
                <img src="/img/pencil.png"/>
              </a>
              <a href="/contacts/${s.id}/~delete"
                 title="${msg['contact.delete']}"
                 rel="popup"
                 class="btn inverse">
                <img src="/img/delete.png"/>
              </a>
            </div>
          </div>
        [/#list]
      [#else ]
        <p class="no-items">${msg['contact.search.empty']}</p>
      [/#if]
      <div id="edit-container" class="w50"></div>
      <div class="centered margin-top margin-bottom">
        <a href="/contacts/" class="btn">${msg['back']}</a>
      </div>
    </div>
    <div class="w33 no-mobiles">
      [#include "search-form.p.ftl"/]
      <div class="centered">
        <img src="/img/find-contact.png"/>
      </div>
    </div>
  </div>
  <div class="clear"></div>
</div>
[/#assign]

[#include "layout.ftl"/]