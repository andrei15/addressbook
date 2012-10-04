[#ftl]

[#assign main]
<form id="uploader-form"
      method="post"
      action="/svc/~upload"
      enctype="multipart/form-data">
  <div id="file-box">
    <div id="prompt">${msg['upload.file']}</div>
    <input id="file"
           type="file"
           name="file"/>
  </div>
</form>

<script type="text/javascript">
  $(function(){
    var input = $("#file");
    var prompt = $("#prompt");
    var form = $("#uploader-form");

    prompt.click(function () {
      input.click();
    });
    input.change(function () {
      form.submit();
    })
  });
</script>
[/#assign]

[#include "layout.ftl"/]