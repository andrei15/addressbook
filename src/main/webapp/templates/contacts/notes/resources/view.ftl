[#ftl]

[#assign __base = "/contacts/${contact.id}/notes/${note.uuid}"/]

[#assign main]
<div class="pad">
  <h2>${msg['resources.add']}</h2>
  <a href="${__base}"
     class="btn"
     title="${msg['notes.toList']}">
  ${msg['notes.toNote']}
  </a>
  <a href="${__base}/resources/img"
     class="btn edit-panel"
     data-container="#edit-container"
     title="${msg['contact.notes.view']}">
  ${msg['resources.image']}
  </a>
  <a href="${__base}/resources/link"
     class="btn edit-panel"
     data-container="#edit-container"
     title="${msg['contact.notes.view']}">
  ${msg['resources.link']}
  </a>
  <a href="${__base}/resources/video"
     class="btn edit-panel"
     data-container="#edit-container"
     title="${msg['contact.notes.view']}">
  ${msg['resources.video']}
  </a>
  [#if note.resources.children?size > 0]
    <hr/>
    [#list note.resources.children as res]
      <div class="files">
      ${res.elemName}
      ${res.id}
      ${res.url}
      </div>
    [/#list]
  [/#if]
</div>
<div id="edit-container"
     class="w50 hidden"></div>

[/#assign]

[#include "layout.ftl"/]