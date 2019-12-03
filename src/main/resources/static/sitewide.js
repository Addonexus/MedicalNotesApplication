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
});