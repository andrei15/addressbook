[#ftl]

[#assign auth]
<a href="/auth/login"
   class="btn">
${msg['login.title']}
</a>
[/#assign]

[#assign main]
<h2>${msg['signup']}</h2>
<form id="signup"
      class="submission partial"
      method="post"
      action="/auth/signup">
  <div class="field-box">
    <label for="l">${msg['user.cn']}</label>
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
    <label for="p">${msg['user.password']}</label>
    <div class="field">
      <div class="input">
        <input id="p"
               type="password"
               name="p"/>
      </div>
    </div>
  </div>
  <div class="field-box">
    <label for="e">${msg['user.email']}</label>
    <div class="field">
      <div class="input">
        <input id="e"
               type="text"
               name="e"/>
      </div>
    </div>
  </div>
  <div class="submits centered">
    <input type="submit" value="${msg['signup.submit']}"/>
  </div>
</form>
[/#assign]

[#include "layout.ftl"/]