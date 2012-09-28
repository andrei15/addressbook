[#ftl]
<!doctype html>
<html>
<head>
  <meta charset="UTF-8"/>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
  <meta name="viewport"
        content="width=device-width, initial-scale=1, maximum-scale=1"/>
  <title>${msg['site.title']}</title>
  <link id="theme-css"
        rel="stylesheet/less"
        type="text/css"
        media="screen, projection"
        href="/css/main.less"/>
  <link rel="icon" type="image/png" href="/img/favicon.png"/>
  <script type="text/javascript" src="/js/jquery.min.js">
  </script>
  <script type="text/javascript" src="/js/less-1.3.0.min.js">
  </script>
  <link href="/css/colorbox.css" rel="stylesheet" type="text/css" />
  <script type="text/javascript" src="/js/jquery.colorbox-min.js">
  </script>
  <script type="text/javascript" src="/js/addressbook.js">
  </script>
  <script type="text/javascript">
    $(function(){
      var cache = jQuery.ajaxSettings.cache;
      jQuery.ajaxSettings.cache = true;
      jQuery.getScript('https://apis.google.com/js/plusone.js', function() {
        var s = document.getElementsByTagName('script')[0];
      });
    })
  </script>
  <script type="text/javascript">
    $(function(){
      var cache = jQuery.ajaxSettings.cache;
      jQuery.ajaxSettings.cache = true;
      jQuery.getScript('http://connect.facebook.net/ru_RU/all.js', function() {
        FB.init({status: true, cookie: true, xfbml: true});
      });
      jQuery.ajaxSettings.cache = cache;
    })
  </script>
</head>
<body>
<div id="header">
  <a href="/" class="home"><img src="/img/AB.png"/></a>
  <div class="right-float">
  [#if !env.principal??]
  ${auth!}
  [#else]
    <div class="userbox">
      <a href="/profile">
        <img src="${env.principal.gravatar("32")}"/>
        <span>${env.principal.login}</span>
      </a>
    </div>
    <div class="logout centered">
      <a href="/auth/logout">${msg['logout.title']}</a>
    </div>
  [/#if]
  </div>
</div>
<div id="content">
  <ul id="notices">
  [#list flash['notices']![] as n]
    <li class="${n.kind}">${n.message} </li>
  [/#list]
  </ul>
${main}
</div>
<div id="footer">
  <div>
    <span>${msg['poweredBy']}</span>
    <a href="http://circumflex.ru"
       target="_blank">
    ${msg['cx']}
    </a>
  </div>
  <ul class="subfooter">
    <li>
      <div class="fb-like"
           data-send="false"
           data-layout="button_count"
           data-width="450"
           data-show-faces="true">
      </div>
    </li>
    <li>
      <g:plusone annotation="inline"></g:plusone>
    </li>
  </ul>
</div>
</body>
</html>