[#ftl]

[#assign main]

<div class="grid">
  <div class="w33">
    <form id="newNote"
          action="/contacts/${contact.id}/notes"
          class="submission created"
          method="post">

      <div class="field-box">
        <label for="t">${msg['contact.notes.title']}</label>
        <div class="field">
          <div class="input">
            <input id="t"
                   name="t"
                   size="25"
                   autofocus="autofocus"
                   type="text"/>
          </div>
        </div>
      </div>

      <div class="field-box">
        <label for="n">${msg['contact.notes.content']}</label>
        <div class="field">
          <div class="input">
            <textarea id="n"
                      rows="10"
                      cols="51"
                      wrap="on"
                     form="newNote"
                     name="n">
            </textarea>
          </div>
        </div>
      </div>

      <div class="submits centered">
        <input type="submit"
               value="${msg['contact.new']}"/>
        <span>${msg['or']}</span>
        <a href="/contacts">${msg['cancel']}</a>
      </div>
    </form>
  </div>
  <div class="w66 no-mobiles">
    <div class="centered">
      <img src="/img/new-contact.png"/>
    </div>
  </div>
</div>
<div class="clear"></div>
[/#assign]

[#include "layout.ftl"/]