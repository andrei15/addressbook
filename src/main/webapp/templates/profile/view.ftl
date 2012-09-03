[#ftl]

[#assign user = env.principal]

[#assign main]
<h2 xmlns="http://www.w3.org/1999/html">${msg['user.profile']}</h2>
<div class="profile">
  <div class="title">${msg['user.login']}: <span>${user.login}</span></div>
  <div class="title">${msg['user.email']}: <span>${user.email}</span> </div>
</div>



<div class="submits centered" >
  <a href="/profile/~edit">${msg['user.profile.edit']}</a>
</div>
[/#assign]

[#include "layout.ftl"/]