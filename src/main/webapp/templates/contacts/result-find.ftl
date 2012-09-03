[#ftl]

[#assign user = env.principal]

[#assign main]
<div class="pad margin-top">
  [#--<h2>${msg['contacts']}</h2>--]
  [#if findr?size > 0]
    [#list findr as f]
      <div class="contact">
        <img src="${f.gravatar("32")}" class="icon"/>
        <div class="title">${f.fullName}</div>
        <div class="subtle">
          <span>${f.phone}</span> &mdash;
          <a class="mail"
             href="mailto:${f.email}">${f.email}</a>
        </div>

      </div>
    [/#list]
  [#else ]
    <p class="no-items">${msg['contact.find.empty']}</p>
  [/#if]
</div>
[/#assign]

[#include "layout.ftl"/]