[#ftl]
<form action="/profile/~edit-password"
      class="submission partial"
      method="post">
  <h2>${msg['user.password.edit']}</h2>

  <div class="field-box">
    <label for="oldPassword">${msg['user.password.old']}</label>
    <div class="field">
      <div class="input">
        <input id="oldPassword"
               name="oldPassword"
               size="25"
               autofocus="autofocus"
               type="text"/>
      </div>
    </div>
  </div>
  <div class="field-box">
    <label for="newPassword">${msg['user.password.new']}</label>
    <div class="field">
      <div class="input">
        <input id="newPassword"
               name="newPassword"
               size="25"
               type="text"/>
      </div>
    </div>
  </div>
  <div class="field-box">
    <label for="confirmPassword">${msg['user.password.confirm']}</label>
    <div class="field">
      <div class="input">
        <input id="confirmPassword"
               name="confirmPassword"
               size="25"
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