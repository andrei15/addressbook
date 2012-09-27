[#ftl]

[#assign main]
<div class="grid pad">
  <div class="w50">
    <h2>${contact.fullName}</h2>
    <div class="notesview">
      <div class="title">${note.title}</div>
      <div class="note">${note.getNote!""}</div>
      [#if note.files.children?size > 0]
        [#list note.files.children as file]
          <div class="files">
            <a href="/contacts/${contact.id}/notes/${note.uuid}/file/${file.uuid}/">${file.originalFileName}</a>
            <a href="/contacts/${contact.id}/notes/${note.uuid}/file/${file.uuid}/~filedelete" rel="popup">&times</a>
          </div>
        [/#list]
      [/#if]
      <div class="ctls">
        <a href="/contacts/${contact.id}/notes/${note.uuid}/~edit"
           class="btn"
           title="${msg['contact.notes.view']}">
          <img src="/img/pencil.png"/>
        </a>
        <a href="/contacts/${contact.id}/notes/${note.uuid}/~delete"
           class="btn inverse"
           rel="popup"
           title="${msg['contact.notes.view']}">
          <img src="/img/delete.png"/>
        </a>
      </div>
    </div>
  </div>
</div>
[/#assign]

[#include "layout.ftl"/]