[#ftl]

[#assign user = env.principal]

[#assign main]
<h2 xmlns="http://www.w3.org/1999/html">${msg['user.profile']}</h2>
<p>${msg['user.login']}:  ${user.login}</p>
<p>${msg['user.email']}:  ${user.email}</p>


<div class="submits centered" >
  <a href="/profile/~edit">${msg['user.profile.edit']}</a>
</div>
[/#assign]

[#include "layout.ftl"/]