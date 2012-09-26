[#ftl]

[#assign main]
<div class="grid pad">
  <div class="w50">
    <h2>${msg['contact.note.edit']}</h2>
    <form id ="editNote"
          action="/contacts/${contact.id}/notes/${note.uuid}"
          class="submission"
          method="post"
          enctype="multipart/form-data">

      <div class="field-box">
        <label for="t">${msg['contact.notes.title']}</label>
        <div class="field">
          <div class="input">
            <input id="t"
                   name="t"
                   size="25"
                   autofocus="autofocus"
                   value="${note.title!""}"
                   type="text"/>
          </div>
        </div>
      </div>

      <div class="field-box">
        <label for="n">${msg['contact.notes.content']}</label>
        <div class="textarea">
          <div class="input">
            <textarea id="n"
                      rows="10"
                      cols="79"
                      form="editNote"
                      name="n">${note.getNote!""}
            </textarea>
          </div>
        </div>
      </div>
      [#include "file-upload.ftl"/]

      <div class="submits centered">
        <input type="submit"
               value="${msg['notes.edit']}"/>
        <span>${msg['or']}</span>
        <a href="/contacts/${contact.id}/notes/${note.uuid}">${msg['cancel']}</a>
      </div>
    </form>
  </div>
</div>
[/#assign]

[#include "layout.ftl"/]