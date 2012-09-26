[#ftl]

[#assign main]
<div class="grid pad">

  <div class="w50 hide50">

    <h2>${contact.fullName}</h2>
    <img src="/img/icons/48/mobile_phone.png"/> <span class="contactinfo">${contact.phone}</span>
    <p></p>
    <img src="/img/icons/48/mail.png"/> <span class="contactinfo"><a class="mail" href="mailto:${contact.email}">${contact.email}</a></span>
    <p></p>
    [#if contact.address!?length > 0]
      <img src="/img/icons/48/home.png"/> <span class="contactinfo">${contact.address}</span>
    [/#if]
    <div class="centered margin-top margin-bottom clear">
      <a href="/contacts/${contact.id}/~edit"
         class="btn editpanel"
         data-container="#edit-container">
      ${msg['contact.edit']}
      </a>
      <a href="/contacts/${contact.id}/~delete"
         rel="popup"
         class="btn inverse">
      ${msg['contact.delete']}
      </a>
      <a href="/contacts/${contact.id}/notes"
         class="btn">
      ${msg['notes.title']}
      </a>
    </div>
  </div>

  <div id="edit-container" class="w50"></div>

</div>
[/#assign]

[#include "layout.ftl"/]