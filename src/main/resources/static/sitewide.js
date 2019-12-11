$(document).ready(function () {
  $(".sidenav").sidenav();
});

$(document).ready(function () {
  $(".modal").modal();
});

$(document).ready(function () {
  $(".datepicker").datepicker();
  col = document.getElementsByClassName("datepicker-date-display");
  console.log(col[0])
  col[0].style.backgroundColor = "#6666ff";
});



notificationList = document.getElementById("notifications");
console.log(notificationList);
refreshNotifications();

function refreshNotifications() {
  while (notificationList.firstChild) {
    notificationList.removeChild(notificationList.firstChild);
  }

  $.get("/api/getAllNotifications", function (data) {
    // if (windowUrl.pathname.includes("diagnosis")) {
    //   console.log("diagnosis num: " + lastInt);
    //   for (var i = 0; i < data.length; i++) {
    //     if (data[i].diagnosisLink.id == lastInt) {
    //     }
    //   }
    // }

    var needToRead = [];
    var needToDo = [];
    var done = [];

    for (var i = 0; i < data.length; i++) {
      var a = document.createElement("a");
      a.appendChild(
        document.createTextNode(
          "Update information for " + data[i].diagnosisLink.name
        )
      );
      var icon = document.createElement("i");
      icon.setAttribute("class", "material-icons right");
      icon.appendChild(document.createTextNode("notifications_active"));

      icon.style.color = "#ccccff";
      icon.style.visibility = data[i].read ? "hidden" : "visible";

      a.appendChild(icon);
      a.setAttribute("id", data[i].id);
      a.setAttribute("class", "collection-item");
      a.setAttribute("href", "/diagnosis/" + data[i].diagnosisLink.id);
      a.style.fontWeight = data[i].read ? "200" : "600";
      a.style.backgroundColor = data[i].done ? "#eeeeee" : "#ffffff";
      a.style.textDecoration = data[i].done ? "line-through" : "none";

      if (data[i].read == false) {
        needToRead.unshift(a);
      } else {
        if (data[i].done == false) {
          needToDo.unshift(a);
        } else {
          done.unshift(a);
        }
      }

      // notificationList.style.visibility = "visible";
    }

    divider = document.createElement("div");
    divider.setAttribute("class", "divider");

    for (let i = 0; i < needToRead.length; i++) {
      notificationList.appendChild(needToRead[i]);
    }

    for (let i = 0; i < needToDo.length; i++) {
      notificationList.appendChild(needToDo[i]);
    }

    notificationList.appendChild(divider);

    for (let i = 0; i < done.length; i++) {
      notificationList.appendChild(done[i]);
    }

    if (needToRead.length > 0) {
      notificationBell = document.getElementById("notification-bell");
      notificationBell.classList.remove("white-text");
      notificationBell.classList.add("blue-text");
      notificationBell.classList.add("text-lighten-3");
      notificationBell.innerHTML = "notifications_active";
    }

    if (notificationList.length > 0) {
      notificationList.style.visibility = "visible";
    }

    notificationList.style.visibility =
      notificationList.childNodes.length > 0 ? "visible" : "hidden";
  });
}

console.log(document.getElementsByClassName("calendar-form"));
$("#calendar-form").submit(function (e) {
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
    success: function (response) {
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
        listOfCasesForDay.style.visibility = "visible";
        listOfCasesForDay.style.marginBottom = "25px";
      }
    },
    error: function (json) {
      alert("Please enter a valid date!");
    }
  });
});

var form = document.getElementById("logout-form");

document.getElementById("logout").addEventListener("click", function () {
  form.submit();
});
