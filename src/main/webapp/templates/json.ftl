[#ftl]
{
  [#if redirect??]
    "redirect": "${redirect}",
  [/#if]
"notices" : [
  [#list flash['notices']![] as notice]
    {
      "kind" : "${notice.kind}",
      "msg"  : "${notice.message}"
    } [#if notice_has_next],[/#if]
  [/#list]
  ]
}