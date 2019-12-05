$(document).ready(function() {
  $(".sidenav").sidenav();
});

$(document).ready(function() {
  $(".modal").modal();
});

$(document).ready(function() {
  $(".datepicker").datepicker();
});

notificationList = document.getElementById("notifications");
console.log(notificationList);
$.get("/api/getAllNotifications", function(data) {
  var listItem = document.createElement
  for (var i = 0; i < data.length; i++) {
    console.log("notif " + i);
    console.log(data[i]);
    var li = document.createElement("a");
    li.appendChild(document.createTextNode("Remember to update the information for " + data[i].diagnosisLink.name));
    var icon = document.createElement("i");
    icon.setAttribute("class", "material-icons right");
    icon.appendChild(document.createTextNode("notifications_active"));
    icon.style.color = "#ccccff";
    li.appendChild(icon);
    li.setAttribute("id", data[i].id);
    li.setAttribute("class", "collection-item");
    li.setAttribute("href", "/diagnosis/" + data[i].diagnosisLink.id);
    notificationList.appendChild(li);
    notificationList.style.visibility = "visible";
  }
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
      while (listOfCasesForDay.firstChild) {
        listOfCasesForDay.removeChild(listOfCasesForDay.firstChild);
      }
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
        listOfCasesForDay.style.marginBottom = "25px"
      }
    },
    error: function(json) {
      alert("Please enter a valid date!");
    }
  });
});