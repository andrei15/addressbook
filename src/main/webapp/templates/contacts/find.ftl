[#ftl]

[#assign errors = flash["errors"]![]/]

[#assign main]
  [#if errors?size > 0]
  <ul>
    [#list errors as e]
      <li>${e}</li>
    [/#list]
  </ul>
  [/#if]
<div class="grid pad">
  <div class="w33">
    <form action="/contacts/find"
          class="submission"
          method="post">


      <div class="field-box">
        <label for="n">${msg['contact.name']}</label>
        <div class="field">
          <div class="input">
            <input id="n"
                   name="n"
                   size="25"
                   [#--value="${contact.name!""}"--]
                   autofocus="autofocus"
                   type="text"/>
          </div>
        </div>
      </div>
      <div class="field-box">
        <label for="s">${msg['contact.surname']}</label>
        <div class="field">
          <div class="input">
            <input id="s"
                   name="s"
                   size="25"
                   [#--value="${contact.surname!""}"--]
                   type="text"/>
          </div>
        </div>
      </div>
      <div class="field-box">
        <label for="p">${msg['contact.phone']}</label>
        <div class="field">
          <div class="input">
            <input id="p"
                   name="p"
                   size="25"
                   [#--value="${contact.phone!""}"--]
                   type="text"/>
          </div>
        </div>
      </div>
      <div class="field-box">
        <label for="e">${msg['contact.email']}</label>
        <div class="field">
          <div class="input">
            <input id="e"
                   name="e"
                   size="25"
                   [#--value="${contact.email!""}"--]
                   type="text"/>
          </div>
        </div>
      </div>
      <div class="field-box">
        <label for="a">${msg['contact.address']}</label>
        <div class="field">
          <div class="input">
            <input id="a"
                   name="a"
                   size="25"
                   [#--value="${contact.address!""}"--]
                   type="text"/>
          </div>
        </div>
      </div>

      <div class="submits centered">
        <input type="submit"
               value="${msg['contact.find.submit']}"/>
        <span>${msg['or']}</span>
        <a href="/contacts">${msg['cancel']}</a>

      </div>
    </form>
  </div>
  <div class="w66 no-mobiles">
    <div class="centered">
      <img src="/img/find-contact.png"/>
    </div>
  </div>
</div>
<div class="clear"></div>


[/#assign]
[#include "layout.ftl"/]