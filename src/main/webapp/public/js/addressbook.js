$(function(){
  setFormAjax($(".partial"));
  getEditPanelByClick($(".editpanel"));
  hideEditPanel();
  setColorboxPopup();
});

//notices
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
    $("#notices li:not(.initialized)").each(function(){
      var li = $(this);
      li.addClass("initialized");
      var handle = $("<div class='handle'>&times;</div>");
      li.append(handle);
      handle.click(function() {
        li.animate({
          "opacity": 0
        }, 500, function(){
          li.remove();
        })
      });
      //Timeout remove notices
      setTimeout(function() {
        $("#notices li").animate({
          "opacity": 0
        }, 1200, function(){
          li.remove();
        });
      }, 10000);
    })
  }
};

//remove notices the esc key
$(document).keyup(function(e) {
  if (e.keyCode == 27) {
    $("#notices li").each(function(){
      var li = $(this);
      li.animate({
        "opacity": 0
      }, 500, function(){
        li.remove();
      })
    })
  }
});

$(window).unload(function() {
  if (sessionStorage) {
    sessionStorage.setItem("notices",JSON.stringify(arr))
  }
});

$(window).load(function(){
  var pathname = window.location.pathname;
  if(pathname == "/profile" || pathname.indexOf("contacts")){
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

function setFormAjax(forms) {
  forms.each(function() {
    var $form = $(this);
    $form.addClass("initialized");
    $(this).submit(function(event){
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
}

function setColorboxPopup() {
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
  });
  notices.init();
}

function hideEditPanel() {
  $(".hide50").each(function(){
    var w = $(this);
    w.removeClass("w50");
    w.removeClass("hide50");
    w.addClass("w100");
    w.addClass("hide100");
  });
}

function showEditPanel() {
  $(".hide100").each(function(){
    var w = $(this);
    w.removeClass("w100");
    w.removeClass("hide100");
    w.addClass("w50");
    w.addClass("hide50");
  });
}

function getEditPanelByClick(btnClass) {
  $(btnClass).each(function(){
    var a = $(this);
    var href = a.attr("href");
    var cnt = $(a.attr("data-container"));
    a.click(function (ev) {

      showEditPanel()

      ev.preventDefault();
      $.get(href, {}, function(data){
        cnt.empty().append(data);
        setFormAjax($(".partial", cnt));

        $(".hidepanel").click(function(){
          cnt.empty();
          hideEditPanel();
          $("#notices").empty();
        });

      }, "html");
      return false;
    });
  });
}
