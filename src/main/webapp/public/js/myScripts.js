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

$(function(){
  $("#profile").submit(function(event){
    var $form = $(this);
    var serializedData = $form.serializeArray();
    $.ajax({
      url: $form.attr("action"),
      type: $form.attr("method"),
      data: serializedData,
      dataType:"json",

      success: function(data){
        alert(JSON.stringify(data));
      },
      error: function(data){
        //$('#profile').html(data);
        if (data.status == 404)
          alert("No message Available");
        if (data.status == 502)
          alert("Server is down")
      }
    });
    event.preventDefault();
    return false;
  });
  $.post("/profile", {"e": "my@my.com", "n":"3"}, function(data) {
    alert(JSON.stringify(data))
  }, "json")
});