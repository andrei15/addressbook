[#ftl]

[#assign main]

<div class="pad ">
  <div class="grid">
    <div class="w50">
      <h2>${msg['notes']}: ${contact.fullName}</h2>
      [#if notes?size > 0]
        [#list notes as n]
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
    <div class="w50 no-mobiles">
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