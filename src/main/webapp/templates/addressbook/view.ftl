[#ftl]

[#assign user = currentUser]

[#assign content]
<h1>${msg['addressbook']}: ${user.login}</h1>
<p>${msg['user.password']}: ${user.password}</p>
<p>${msg['user.creationDate']}: ${user.creationDate?string("dd-MM-yyyy")}</p>
[/#assign]

[#include "layout.ftl"/]