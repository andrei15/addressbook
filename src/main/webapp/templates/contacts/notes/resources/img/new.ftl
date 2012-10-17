[#ftl]

[#assign __base = "/contacts/${contact.id}/notes/${note.uuid}"/]

<form action="${__base}/resources/img"
      class="submission partial"
      method="post">
  <div class="field-box">
    <label for="t">${msg['resources.id']}</label>
    <div class="field">
      <div class="input">
        <input id="t"
               name="t"
               size="25"
               autofocus="autofocus"
               value="${(res.id)!""}"
               type="text"/>
      </div>
    </div>
  </div>
  <div class="field-box">
    <label for="n">${msg['resources.url']}</label>
    <div class="field">
      <div class="input">
        <input id="n"
               name="n"
               size="25"
               autofocus="autofocus"
               value="${(res.title)!""}"
               type="text"/>
      </div>
    </div>
  </div>
  <div class="file-uploader">
    <iframe src="/svc/~upload">
    </iframe>
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