$(function(){
  $("[rel=popup]").each(function() {
    var a = $(this);
    a.click(function(){
      a.colorbox({
        title:" ",
        opacity:"0.75",
        href: a.attr("href")
      });
    });
  })
});