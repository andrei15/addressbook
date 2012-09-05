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
          </div>
        [/#list]
      [#else ]
        <p class="no-items">${msg['contact.search.empty']}</p>
      [/#if]
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