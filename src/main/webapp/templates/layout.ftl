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
  <script type="text/javascript" src="/js/myScripts.js">
  </script>

</head>
<body>
<div id="header">
  <a href="/" class="home"><img src="/img/AB.png"/></a>
  <div class="right-float">
  [#if !env.principal??]
    <a href="/auth/login" class="btn">
    ${msg['login.title']}
    </a>
  [#else]
    <div class="userbox">
      <a href="/profile">
        <img src="${env.principal.gravatar("32")}"/>
        <span>${env.principal.login}</span>
      </a>
    </div>
    <div class="logout">
      <a href="/auth/logout">${msg['logout.title']}</a>
    </div>
  [/#if]
  </div>
</div>
<div id="content">
${main}
</div>
<div id="footer" class="clear">
  <span>${msg['poweredBy']} <a href="http://circumflex.ru">Circumflex</a></span>
</div>
</body>
</html>