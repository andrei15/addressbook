[#ftl]

[#assign user = env.principal]
[#assign main]

<div class="grid pad">

  <div class="w33 no-mobiles">
    <div class="centered">
      <img src="${env.principal.gravatar("128")}"/>
    </div>
  </div>

  <div class="w66">
    <h2>${msg['user.profile']}</h2>
    <div class="profile">
      <div class="title">${msg['user.login']}: <span>${user.login}</span></div>
      <div class="title">${msg['user.email']}: <span>${user.email}</span> </div>
    </div>
    <div class="submits centered" >
      <a href="/profile/~edit">${msg['user.profile.edit']}</a>
    </div>
  </div>
</div>
<div class="clear"></div>
[/#assign]

[#include "layout.ftl"/]