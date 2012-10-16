[#ftl]

<div class="field-box">
  <label for="t">${msg['resources.id']}</label>
  <div class="field">
    <div class="input">
      <input id="t"
             name="t"
             size="25"
             autofocus="autofocus"
             value="${res.id!""}"
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
             value="${res.title!""}"
             type="text"/>
    </div>
  </div>
</div>
<div class="file-uploader">
  <iframe src="/svc/~upload">
  </iframe>
</div>
