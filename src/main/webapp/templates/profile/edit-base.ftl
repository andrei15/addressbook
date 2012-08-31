[#ftl]

[#assign user = env.principal]
[#assign main]

<form action="/profile/~edit"
      class="submission"
      method="post">
[#--[#include "edit-base.ftl"/]--]
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
  </div>
</form>

[/#assign]
[#include "layout.ftl"/]