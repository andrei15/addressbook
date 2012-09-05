[#ftl]

<form action="/contacts/search"
      class="submission right-float"
      method="get">
  <div class="field-box">
    <div class="field">
      <div class="input">
        <input id="q"
               name="q"
               placeholder="${msg['contact.search.submit']}"
               size="45"
               value="${param['q']!""}"
               autofocus="autofocus"
               type="text"/>
      </div>
    </div>
  </div>
</form>