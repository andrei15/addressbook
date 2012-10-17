[#ftl]

[#assign __base = "/contacts/${contact.id}/notes/${note.uuid}"/]

<form action="${__base}/resources/link"
      class="submission partial"
      method="post">
  <div class="field-box">
    <label for="id">${msg['resources.id']}</label>
    <div class="field">
      <div class="input">
        <input id="id"
               name="id"
               size="25"
               autofocus="autofocus"
               value="${(res.id)!""}"
               type="text"/>
      </div>
    </div>
  </div>
  <div class="field-box">
    <label for="url">${msg['resources.url']}</label>
    <div class="field">
      <div class="input">
        <input id="url"
               name="url"
               size="25"
               autofocus="autofocus"
               value="${(res.title)!""}"
               type="text"/>
      </div>
    </div>
  </div>
  <div class="submits centered">
    <input type="submit"
           value="${msg['contact.edit.submit']}"/>
    <span>${msg['or']}</span>
    <a href="javascript:;"
       class="hide-panel">${msg['cancel']}
    </a>
  </div>
</form>