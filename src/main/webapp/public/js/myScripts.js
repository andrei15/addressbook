$(function(){
  $("[rel=popup]").each(function() {
    var a = $(this);
    a.click(function(){
      $.colorbox.remove();
      a.colorbox({
        title:" ",
        opacity:"0.75",
        href: a.attr("href"),
        close: "&times;"
      });
    });
  })
});