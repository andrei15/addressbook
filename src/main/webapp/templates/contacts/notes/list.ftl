[#ftl]

[#assign main]
<div class="pad ">
  <div class="right-align">
    <a href="/contacts/${contact.id}/notes/~new"
       class="btn">
    ${msg['notes.new']}
    </a>
  </div>
  <div class="grid">
    <div class="w66">
      <h2>${msg['notes']}: ${contact.title}</h2>
      [#if contact.notes.children?size > 0]
        [#list contact.notes.children as n]
          <a href="/contacts/${contact.id}/notes/${n.uuid}"
             title="${msg['contact.notes.open']}">
            <div class="notes">
              <div class="title">${n.title}</div>
            </div>
          </a>
        [/#list]
      [#else ]
        <p class="no-items">${msg['contact.notes.empty']}</p>
      [/#if]
    </div>
    <div class="w33 no-mobiles">
      <div class="centered">
        <a href="/contacts/${contact.id}/notes/~new"
           title="${msg['contact.note']}">
          <img src="/img/notes.png"/>
        </a>
      </div>
    </div>
  </div>
</div>
[/#assign]

[#include "layout.ftl"/]