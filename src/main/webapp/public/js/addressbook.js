$(function () {
  ui.init()
});

var ui = {
  init: function (ctx) {
    notices.init();
    initAjaxForms(ctx);
    initPanelEditForm(ctx);
    initColorbox();
  }
};

//notices
var arr = new Array();
var notices = {
  add: function (notice) {
    arr.push(notice)
  },
  save: function (data) {
    arr = new Array();
    if (data.notices) {
      for (var i in data.notices) {
        var n = data.notices[i];
        notices.add(n);
      }
    }
  },
  init: function () {
    $("#notices li:not(.initialized)").each(function () {
      var li = $(this);
      li.addClass("initialized");
      var handle = $("<div class='handle'>&times;</div>");
      li.append(handle);
      handle.click(function () {
        li.animate({
          "opacity": 0
        }, 500, function () {
          li.remove();
        })
      });
      //Timeout remove notices
      setTimeout(function () {
        $("#notices li").animate({
          "opacity": 0
        }, 1200, function () {
          li.remove();
        });
      }, 10000);
    })
  },

  removeAll: function () {
    $("#notices li").each(function () {
      var li = $(this);
      li.animate({
        "opacity": 0
      }, 500, function () {
        li.remove();
      })
    })
  }
};

//remove notices the esc key
$(document).keyup(function (e) {
  if (e.keyCode == 27) {
    notices.removeAll()
  }
});

$(window).unload(function () {
  if (sessionStorage) {
    sessionStorage.setItem("notices", JSON.stringify(arr));
  }
});

$(window).load(function () {
  var pathname = window.location.pathname;
  if (pathname == "/profile" || pathname.indexOf("contacts")) {
    var variable = JSON.parse(sessionStorage.getItem("notices"));
    sessionStorage.clear();
    $("#notices").empty();
    $.each(variable, function (idx, n) {
      var li = $("<li></li>");
      li.html(n.msg);
      li.addClass(n.kind);
      $("#notices").append(li)
    });
  }
});

function initAjaxForms(ctx) {
  $(".partial", ctx).each(function () {
    var $form = $(this);
    $form.addClass("initialized");
    $(this).submit(function (event) {
      var serializedData = $form.serializeArray();
      $.ajax({
        url: $form.attr("action"),
        type: $form.attr("method"),
        data: serializedData,
        dataType: "json",

        success: function (data) {
          if (data.redirect) {
            notices.save(data);
            window.location.replace(data.redirect);
          }
          if (data.notices) {
            $("#notices").empty();
            $.each(data.notices, function (idx, n) {
              var li = $("<li></li>");
              li.html(n.msg);
              li.addClass(n.kind);
              $("#notices").append(li)
            });
            notices.init();
          }
        },
        error: function (data) {
          if (data.status == 404) {
            alert("No message Available");
          }
          if (data.status == 502) {
            alert("Server is down")
          }
        }
      });
      event.preventDefault();
      return false;
    });
  });
}

function initColorbox() {
  $("[rel=popup]").each(function () {
    var a = $(this);
    a.click(function () {
      $.colorbox.remove();
      a.colorbox({
        title: " ",
        opacity: "0.75",
        href: a.attr("href"),
        close: "&times;",
        onComplete: function () {
          $(".close").each(function () {
            $(this).click($.colorbox.close)
          });
        }
      });
    });
  });
  notices.init();
}

function initPanelEditForm(ctx) {
  $(".edit-panel", ctx).each(function () {
    var a = $(this);
    var href = a.attr("href");
    var cnt = $(a.attr("data-container"));
    a.click(function (ev) {
      showEditPanel();
      ev.preventDefault();
      $.get(href, {}, function (data) {
        cnt.empty().append(data);
        initAjaxForms(cnt);

        $(".hide-panel").click(function () {
          cnt.empty();
          hideEditPanel();
          $("#notices").empty();
        });

      }, "html");
      return false;
    });
  });

  function hideEditPanel() {
    $(".w50:last-child").each(function () {
      var w = $(this);
      w.addClass("hidden");
    });
    $(".w50:not(hidden)").each(function () {
      $(this)
        .removeClass("w50")
        .addClass("w100")
    });
  }

  function showEditPanel() {
    $(".w50").each(function () {
      var w = $(this);
      w.removeClass("hidden");
    });
    $(".w100").each(function () {
      $(this)
        .removeClass("w100")
        .removeClass("hidden")
        .addClass("w50")
    })
  }
}
