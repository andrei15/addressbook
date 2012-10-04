[#ftl]
<html>
<head>
  <link rel="stylesheet/less"
        type="text/css"
        media="screen, projection"
        href="${env.public("/css/main.less")}"/>
  <link rel="stylesheet/less"
        type="text/css"
        media="screen, projection"
        href="${env.public("/css/fileupload.less")}"/>
  <script type="text/javascript" src="/js/jquery.min.js">
  </script>
  <script type="text/javascript" src="/js/less-1.3.0.min.js">
  </script>
</head>
<body id="content">
${main}
</body>
</html>