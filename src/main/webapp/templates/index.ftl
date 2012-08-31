[#ftl]

[#assign main]
<div class="grid margin-top pad">
  <div class="w66">
    <div class="centered">
      <img src="/img/ab_logo.png"/>
    </div>
  </div>
  <div class="w33">
    <h2>${msg['signup']}</h2>
    <form class="submission"
          method="post"
          action="/auth/signup">
      <div class="field-box">
        <label for="cn">${msg['signup.cn']}</label>
        <div class="field">
          <div class="input">
            <input id="cn"
                   type="text"
                   name="cn"/>
          </div>
        </div>
      </div>
      <div class="field-box">
        <label for="pswd">${msg['signup.password']}</label>
        <div class="field">
          <div class="input">
            <input id="pswd"
                   type="password"
                   name="p"/>
          </div>
        </div>
      </div>
      <div class="field-box">
        <label for="e">${msg['signup.email']}</label>
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
  </div>
</div>
[/#assign]

[#include "layout.ftl"/]