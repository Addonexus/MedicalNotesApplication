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

  var listOfCasesForDay = document.getElementById("casesForTheDay");
  console.log(listOfCasesForDay);


  $.ajax({
    contentType: "application/json; charset=utf-8",
    dataType: "json",
    type: "POST",
    url: url,
    data: JSON.stringify(formData),
    success: function(response) {
      console.log(response.casesList);
      for (let i = 0; i < response.casesList.length; i++) {
        console.log(response.casesList[i].name);
        var li = document.createElement("a");
        li.appendChild(document.createTextNode(response.casesList[i].name));
        li.setAttribute("id", response.casesList[i].id);
        li.setAttribute("class", "collection-item");
        li.setAttribute("href", "/case/" + response.casesList[i].id);
        listOfCasesForDay.appendChild(li);
        listOfCasesForDay.style.visibility = "visible"
      }
    },
    error: function(json) {
      alert("Please enter a valid date!");
    }
  });
});