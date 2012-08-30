[#ftl]

[#assign error = flash["error"]!/]

[#assign content]
  [#if error??]<p>${error}</p>[/#if]
[/#assign]

[#include "layout.ftl"/]