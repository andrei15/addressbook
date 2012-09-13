[#ftl]

[#assign main]
<div class="grid pad">

  <div class="w66">

    <h2>${contact.fullName}</h2>
    <img src="/img/icons/48/mobile_phone.png"/> <span class="contactinfo">${contact.phone}</span>
    <p></p>
    <img src="/img/icons/48/mail.png"/> <span class="contactinfo"><a class="mail" href="mailto:${contact.email}">${contact.email}</a></span>
    <p></p>
    [#if contact.address!?length > 0]
      <img src="/img/icons/48/home.png"/> <span class="contactinfo">${contact.address}</span>
    [/#if]
    <div class="centered margin-top margin-bottom clear">
    [#--<a href="/contacts/${contact.id}/~edit"--]
    [#--class="btn edform"--]
    [#--data-container="#edit-container"--]
    [#--title="${msg['contact.edit']}">--]
    [#--<img src="/img/pencil.png"/>--]
    [#--</a>--]
      <a href="/contacts/${contact.id}/~delete"
         rel="popup"
         class="btn inverse">${msg['contact.delete']}
      </a>
    </div>
  </div>
  <div class="w33 no-mobiles">
    <div class="centered">
      <img src="${contact.gravatar("128")}"/>
    </div>
  </div>
</div>
<div class="clear"></div>
[/#assign]

[#include "layout.ftl"/]