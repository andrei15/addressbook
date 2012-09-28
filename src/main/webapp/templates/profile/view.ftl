[#ftl]

[#assign user = env.principal]

[#assign main]

<div class="pad ">
  <div class="grid">

    <div class="w100">
      <h2>${msg['user.profile']}</h2>
      <form action="/profile"
            id="profile"
            class="submission partial"
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

        <div class="field-box">
          <label for="p">${msg['user.submit.password']}</label>
          <div class="field">
            <div class="input">
              <input id="p"
                     name="p"
                     size="25"
                     type="password"/>
            </div>
          </div>
        </div>

        <div class="submits centered" >
          <input type="submit"
                 value="${msg['user.profile.edit']}"/>
          <a href="/profile/~edit-password"
             class="btn edit-panel"
             data-container="#editPasswordContainer">
          ${msg['user.password.edit']}
          </a>
        </div>
      </form>
    </div>
    <div id="editPasswordContainer" class="w50"></div>
  </div>

</div>
[/#assign]

[#include "layout.ftl"/]