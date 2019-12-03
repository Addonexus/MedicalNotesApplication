$(document).ready(function() {
  $(".sidenav").sidenav();
});

$(document).ready(function() {
  $(".modal").modal();
});

$(document).ready(function() {
  $(".datepicker").datepicker();
});

console.log(document.getElementsByClassName("calendar-form"));
$("#calendar-form").submit(function(e) {
  e.preventDefault();
  var url = "/api/getCasesByDate/";

  var formData = {
    name: document.getElementById("date").value
  };

  $.ajax({
    contentType: "application/json; charset=utf-8",
    dataType: "json",
    type: "POST",
    url: url,
    data: JSON.stringify(formData),
    success: function(json) {
      console.log(json);
    },
    error: function(json) {
      alert("error!");
    }
  });
});