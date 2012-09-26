[#ftl]

[#assign main]

<div class="grid">
  <div class="w50">
    <h2>${msg['contact.note.new']}</h2>
    <form id="newNote"
          action="/contacts/${contact.id}/notes"
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
                      form="newNote"
                      name="n"></textarea>
          </div>
        </div>
      </div>
      [#include "file-upload.ftl"/]
      <div class="submits centered">
        <input type="submit"
               value="${msg['notes.new']}"/>
        <span>${msg['or']}</span>
        <a href="/contacts/${contact.id}/notes">${msg['cancel']}</a>
      </div>
    </form>

  </div>
  <div class="w50 no-mobiles">
    <div class="centered">
      <img src="/img/icons/128/note.png"/>
    </div>
  </div>
</div>

[/#assign]

[#include "layout.ftl"/]