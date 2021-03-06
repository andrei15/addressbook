[#ftl]

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
  <div class="field">
    <div class="input">
      <textarea id="n"
                rows="10"
                cols="79"
                name="n">${note.plainText!""}</textarea>
    </div>
  </div>
</div>
<div class="file-uploader">
  <iframe src="/svc/~upload">
  </iframe>
</div>
