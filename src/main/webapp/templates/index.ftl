[#ftl]

[#assign main]
<div class="grid margin-top pad">
  <div class="w66">
    <div class="centered">
        <img src="/img/ab_logo.png">
    </div>
  </div>
  <div class="w33">
    <h2>${msg['login']}</h2>
    <form id="login"
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
        <span>${msg['or']}</span>
        <a href="/auth/signup">${msg['signup.submit']}</a>
      </div>
    </form>
  </div>
</div>
[/#assign]

[#include "layout.ftl"/]