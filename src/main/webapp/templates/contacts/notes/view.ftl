[#ftl]

[#assign __base = "/contacts/${contact.id}/notes/${note.uuid}"/]

[#assign main]
<div class="pad">
  <h2>${contact.title}</h2>
  <div class="noteview">
    <div class="btn-group right-float">
      <a href="${__base}/~edit"
         class="btn"
         title="${msg['contact.notes.view']}">
        <img src="/img/pencil.png"/>
      </a>
      <a href="${__base}/~delete"
         class="btn inverse"
         rel="popup"
         title="${msg['contact.notes.view']}">
        <img src="/img/delete.png"/>
      </a>
    </div>
    <div class="title">${note.title}</div>
    <div class="note">${note.html}</div>
    [#if note.files.children?size > 0]
      <hr/>
      [#list note.files.children as fd]
        <div class="files">
          <a href="${__base}/file/${fd.uuid}">${fd.originalFileName}</a>
          <a href="${__base}/file/${fd.uuid}/~delete" rel="popup">
            <img src="/img/icons/16/delete.png"/>
          </a>
        </div>
      [/#list]
    [/#if]
  </div>
  <div class="centered margin-top">
    <a href="/contacts/${contact.id}/notes"
       class="btn">${msg['notes.toList']}</a>
  </div>
</div>
[/#assign]

[#include "layout.ftl"/]