[#ftl]

<div class="field-box">
  <label for="n">${msg['contact.name']}</label>
  <div class="field">
    <div class="input">
      <input id="n"
             name="n"
             size="25"
             value="${contact.name!""}"
             autofocus="autofocus"
             type="text"/>
    </div>
  </div>
</div>
<div class="field-box">
  <label for="s">${msg['contact.surname']}</label>
  <div class="field">
    <div class="input">
      <input id="s"
             name="s"
             size="25"
             value="${contact.surname!""}"
             type="text"/>
    </div>
  </div>
</div>
<div class="field-box">
  <label for="p">${msg['contact.phone']}</label>
  <div class="field">
    <div class="input">
      <input id="p"
             name="p"
             size="25"
             value="${contact.phone!""}"
             type="text"/>
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
<div class="field-box">
  <label for="a">${msg['contact.address']}</label>
  <div class="field">
    <div class="input">
      <input id="a"
             name="a"
             size="25"
             value="${contact.address!""}"
             type="text"/>
    </div>
  </div>
</div>