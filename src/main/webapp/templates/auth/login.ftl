[#ftl]
[#assign auth]
<a href="/auth/signup" class="btn">
${msg['signup.submit']}
</a>
[/#assign]
[#assign main]
  [#include "login.p.ftl"/]
[/#assign]

[#include "layout.ftl"/]