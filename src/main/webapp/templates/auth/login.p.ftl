[#ftl]
<h2>${msg['login']}</h2>
<form id ="login"
      action="/auth/login"
      class="submission"
      method="post">
  <div class="field-box">
    <label for="l">${msg['login.cn']}</label>
    <div class="field">
      <div class="input">
        <input id="l"
               type="text"
               autofocus="autofocus"
               name="l"/>
      </div>
    </div>
  </div>
  <div class="field-box">
    <label for="p">${msg['login.password']}</label>
    <div class="field">
      <div class="input">
        <input id="p"
               type="password"
               name="p"/>
      </div>
    </div>
  </div>
  <div class="submits centered">
    <input type="submit" value="${msg['login.submit']}">
  </div>
</form>