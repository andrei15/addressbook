[#ftl]

<form action="/contacts/${contact.id}/notes/${note.uuid}/~email"
      class="submission partial"
      method="post">
  <h2>${msg['notes.send']}</h2>
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
    <label for="n">${msg['contact.notes.note']}</label>
    <div class="field">
      <div class="input">
        <textarea id="n"
                  rows="10"
                  cols="79"
                  name="n">${note.plainText!""}</textarea>
      </div>
    </div>
  </div>
  <div class="field-box">
    <label for="e">${msg['contact.email']}</label>
    <div class="field">
      <div class="input">
        <input id="e"
               name="e"
               size="25"
               value="${contact.email!""}"
               type="text"/>
      </div>
    </div>
  </div>
  <div class="submits centered">
    <input type="submit"
           value="${msg['notes.send.title']}"/>
    <span>${msg['or']}</span>
    <a href="javascript:;"
       class="close">
    ${msg['cancel']}
    </a>
  </div>
</form>