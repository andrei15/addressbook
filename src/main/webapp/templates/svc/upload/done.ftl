[#ftl]

[#assign errors = flash["notices"]!/]

[#assign main]
  [#if errors?size > 0]
    [#assign error = errors[0]/]
  <div class="upload-pill">
    <img src="/img/icons/32/delete.png"
         class="icon"/>
    <div class="title">${error.message}</div>
    <div class="ctls">
      <a href="/svc/~upload"
         title="${msg['upload.newFile']}"
         class="btn">
        <img src="${env.public("/img/icons/32/refresh.png")}"/>
      </a>
    </div>
  </div>
  [#else]
  <div class="upload-pill">
    <input type="hidden" name="uuid" value="${fileDescription.uuid}"/>
    <input type="hidden" name="ext" value="${fileDescription.ext}"/>
    <input type="hidden" name="originalName" value="${fileDescription.originalName}"/>
    <img src="/img/icons/32/accept.png"
         class="icon"/>
    <div class="title">
      ${message}
    </div>
    <div class="ctls">
      <a href="/svc/~upload"
         title="${msg['upload.newFile']}"
         class="btn">
        <img src="${env.public("/img/icons/32/refresh.png")}"/>
      </a>
    </div>
  </div>
  [/#if]
[/#assign]

[#include "layout.ftl"/]