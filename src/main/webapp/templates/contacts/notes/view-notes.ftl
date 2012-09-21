[#ftl]

[#assign main]
<div class="grid pad">
  <div class="w50">
    <h2>${contact.fullName}</h2>
    <div class="notesview">
      <div class="title">${note.title}</div>
  ${textNote}
    </div>

  </div>
</div>
</div>
<div class="clear"></div>
[/#assign]

[#include "layout.ftl"/]