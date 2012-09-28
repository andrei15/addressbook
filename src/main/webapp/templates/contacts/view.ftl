[#ftl]

[#assign main]
<div class="right-float btn-group">
  <a href="/contacts/${contact.id}/notes/"
     class="btn"
     title="${msg['contact.notes.view']}">
    <img src="/img/icons/48/note_book.png"/>
  </a>
  <a href="/contacts/${contact.id}/~edit"
     class="btn edit-panel"
     data-container="#edit-container"
     title="${msg['contact.edit']}">
    <img src="/img/pencil.png"/>
  </a>
  <a href="/contacts/${contact.id}/~delete"
     title="${msg['contact.delete']}"
     rel="popup"
     class="btn inverse">
    <img src="/img/delete.png"/>
  </a>
</div>
<h2>${contact.title}</h2>
<div class="grid pad">
  <div class="w100">
    <p>
      <img src="/img/icons/48/mobile_phone.png"/>
      <span class="contactinfo">${contact.phone}</span>
    </p>
    <p>
      <img src="/img/icons/48/mail.png"/>
      <a href="mailto:${contact.email}">${contact.email}</a>
    </p>
    [#if (contact.address!"")?length > 0]
      <p>
        <img src="/img/icons/48/home.png"/>
        <span>${contact.address}</span>
      </p>
    [/#if]
    <div class="centered margin-bottom margin-top">
      <a href="/contacts"
         class="btn">${msg['contacts.toList']}</a>
    </div>
  </div>
  <div id="edit-container" class="w50 hidden"></div>
</div>

[/#assign]

[#include "layout.ftl"/]