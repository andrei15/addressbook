[#ftl]

[#assign errors = flash["errors"]![]/]
[#assign user = env.principal]

[#assign main]
  [#if errors?size > 0]
  <ul>
    [#list errors as e]
      <li>${e}</li>
    [/#list]
  </ul>
  [/#if]
<div class="grid pad">
  <div class="w33">
    <h2>${msg['user.profile.edittitle']}</h2>
    <form action="/profile/~edit"
          class="submission"
          method="post">

      <div class="field-box">
        <label for="n">${msg['user.login']}</label>
        <div class="field">
          <div class="input">
            <input id="n"
                   name="n"
                   size="25"
                   value="${user.login!""}"
                   autofocus="autofocus"
                   type="text"/>
          </div>
        </div>
      </div>
      <div class="field-box">
        <label for="e">${msg['user.email']}</label>
        <div class="field">
          <div class="input">
            <input id="e"
                   name="e"
                   size="25"
                   value="${user.email!""}"
                   type="text"/>
          </div>
        </div>
      </div>
      <div class="submits centered" >
        <input type="submit"
               value="${msg['user.profile.edit']}"/>
        <span>${msg['or']}</span>
        <a href="/profile">${msg['cancel']}</a>
      </div>
    </form>
  </div>
  <div class="w66 no-mobiles">
    <div class="centered">
      <img src="/img/edit-profile.png"/>
    </div>
  </div>
</div>
<div class="clear"></div>

[/#assign]
[#include "layout.ftl"/]