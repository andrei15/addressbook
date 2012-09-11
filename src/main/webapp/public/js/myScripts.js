var arr = new Array();
var notices = {
  add: function(notice) {
    arr.push(notice)
  },
  save: function(data) {
    arr=new Array();
    if (data.notices) {
      for (var i in data.notices) {
        var n = data.notices[i];
        notices.add(n);
      }
    }
  },

  init: function() {
    $("#notices li").click(function() {
      var li = $(this);
      li.animate({
        "opacity": 0
      }, 300, function(){
        li.remove();
      })
    });
  }
};

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

  notices.init();
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
        if (data.redirect){
          notices.save(data);
          window.location.replace(data.redirect);
        }
        if (data.notices) {
          $("#notices").empty();
          $.each(data.notices, function(idx, n) {
            var li = $("<li></li>");
            li.html(n.msg);
            li.addClass(n.kind);
            $("#notices").append(li)
          });
          notices.init();
        }
      },

      error: function(data){
        if (data.status == 404)
          alert("No message Available");
        if (data.status == 502)
          alert("Server is down")
      }
    });
    event.preventDefault();
    return false;
  });
});

$(window).unload(function(){
  if (sessionStorage) {
    sessionStorage.setItem("notices",JSON.stringify(arr))
  }
});

$(window).load(function(){
  var pathname = window.location.pathname;
  if(pathname == "/profile"){
    var variable = JSON.parse(sessionStorage.getItem("notices"));
    sessionStorage.clear();
    $("#notices").empty();
    $.each(variable, function(idx, n) {
      var li = $("<li></li>");
      li.html(n.msg);
      li.addClass(n.kind);
      $("#notices").append(li)
    });
    notices.init();
  }
});

