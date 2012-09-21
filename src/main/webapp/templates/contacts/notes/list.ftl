[#ftl]

[#assign main]
<div class="pad ">
  <div class="grid">
    <div class="w50">
      <h2>${msg['notes']}: ${contact.fullName}</h2>
      [#if notes?size > 0]
        [#list notes as n]
          <div class="notes">
            <div class="title">${n.title}</div>
            <div class="ctls">
              <a href="/contacts/${contact.id}/notes/${n.uuid}"
                 class="btn note"
                 title="${msg['contact.notes.open']}">
                <img src="/img/icons/48/note.png"/>
              </a>
            </div>
          </div>
        [/#list]
      [#else ]
        <p class="no-items">${msg['contact.notes.empty']}</p>
      [/#if]
    </div>
  </div>
</div>
<div class="clear"></div>
[/#assign]

[#include "layout.ftl"/]