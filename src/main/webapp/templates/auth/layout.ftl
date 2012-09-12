[#ftl]

[#assign main]
  [#assign errors = flash['errors']![]/]

  [#if errors?size > 0]
  <ul>
    [#list errors as e]
      <li>${e}</li>
    [/#list]
  </ul>
  [/#if]
<div class="grid margin-top pad">
  <div class="w66">
    <img src="/img/ab_logo.png"/>
  </div>
  <div class="w33">
  ${main}
  </div>
[/#assign]

[#include "../layout.ftl"/]