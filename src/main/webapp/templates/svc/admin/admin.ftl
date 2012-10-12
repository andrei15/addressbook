[#ftl]

[#assign main]
<div class="pad">
  <h2>${msg['admin.query']}</h2>
  <div class="grid margin-top">
    <div class="w100">
      <form action="/svc/admin/query"
            class="submission partial"
            method="post">
        <div class="field-box">
          <label for="q">${msg['admin.query.title']}</label>
          <div class="field">
            <div class="input">
              <input id="q"
                     name="q"
                     autofocus="autofocus"
                     type="text"/>
            </div>
          </div>
        </div>
        <div class="field">
          <div class="input">
            <pre><code id="n">${res!""}</code></pre>
          </div>
        </div>
        <div class="submits centered">
          <input type="submit"
                 value="${msg['admin.run']}"/>
          <span>${msg['or']}</span>
          <a href="/contacts">${msg['cancel']}</a>
        </div>
      </form>
    </div>
  </div>
</div>
<script type="text/javascript">
  $(function(){
    $(document).bind("partial-load.ui", function(ev, data) {
      $("#n").empty().append(data.res)
    })
  })
</script>
[/#assign]

[#include "layout.ftl"/]