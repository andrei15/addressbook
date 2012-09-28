[#ftl]

[#assign auth]
<a href="/auth/signup" class="btn">
${msg['signup.submit']}
</a>
[/#assign]

[#assign main]
<div class="grid pad">
  <div class="w66">
    <div class="centered">
      <img src="/img/ab_logo.png">
    </div>
  </div>
  <div class="w33">
    [#include "/auth/login.p.ftl"/]
  </div>
</div>
[/#assign]

[#include "layout.ftl"/]