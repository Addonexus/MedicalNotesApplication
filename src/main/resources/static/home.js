// Get the window URL
windowUrl = window.location;
console.log("Window URL: " + windowUrl);
lastInt = window.location.pathname.replace(/^\D+/g, "");
console.log(lastInt);

// Show recent cases false by default
var showRecentCases = false;

// $(document).ready(function() {

if (windowUrl.pathname.includes("category")) {
  showRecentCases = true;

  // Refresh / get the list / grid of diagnoses
  setTimeout(refreshListOfDiagnoses, 0);

  // Check if category form is posted
  lookForCategoryFormPost();
}

if (windowUrl.pathname.includes("home")) {
  showRecentCases = true;
}

if (windowUrl.pathname.includes("diagnosis")) {
  showRecentCases = true;
}

if (windowUrl.pathname.includes("case")) {
  showRecentCases = true;
}

if (showRecentCases) {
  getRecentCases();
}
// })

function refreshListOfDiagnoses() {
  var grid = document.getElementById("content-grid");

  while (grid.firstChild) {
    grid.removeChild(grid.firstChild);
  }
  console.log("HI THERE");
  console.log(window.location);

  if (window.location.pathname.includes("category")) {
    $.get("/api/getDiagnosisByCategoryId/" + lastInt, function(data) {
      $(".result").html(data);
      console.log(data);
      var grid = document.getElementById("content-grid");
      for (i = 0; i < data.length; i++) {
        console.log("diagnosis:" + data[i]);
        var a = document.createElement("a");
        var b = document.createElement("button");
        a.setAttribute("href", "/diagnosis/" + data[i].id);
        a.style.width = "100%";
        b.appendChild(document.createTextNode(data[i].name));
        b.setAttribute("class", "btn content-item bigger");
        b.style.fontWeight = "600";
        a.appendChild(b);
        grid.appendChild(a);
      }
    });
  }
}

$.get("/api/returnedDiagnosisInfo/" + lastInt, function(data) {
  $(".result").html(data);
  console.log(data);
  var diagnosisInfoTable = document.getElementById("diaInfoTable");
  for (i = 0; i < data.length; i++) {
    var row = diagnosisInfoTable.insertRow(diagnosisInfoTable.rows.length - 1);
    var key = row.insertCell(-1);
    var value = row.insertCell(-1);

    key.innerHTML = data[i].key;
    value.innerHTML = data[i].value;
  }
});

function getRecentCases() {
  $.ajax({
    type: "GET",
    url: "/recentCases",
    // crossDomain: true,
    contentType: "application/json; charset=utf-8",
    dataType: "json",

    success: function(response) {
      var ids = response.categoryIds;
      var cases = response.casesList;

      // $.get("/recentCases", function(data) {
      //   $(".result").html(data);
      //   console.log("WHAT" + data.categoryIds);
      //   console.log("NICE DATA",data);
      var listOfCases = document.getElementById("recentCases");
      for (i = 0; i < cases.length; i++) {
        var li = document.createElement("a");
        li.appendChild(document.createTextNode(cases[i].name));
        li.setAttribute("id", cases[i].id);
        li.setAttribute("class", "collection-item");
        li.setAttribute("href", "/case/" + cases[i].id);
        listOfCases.appendChild(li);
      }
    },
    error: function(response) {
      console.log(
        "Request Status: " +
          response.status +
          " Status Text: " +
          response.statusText +
          " " +
          " Response Text: " +
          response.responseText
      );
    }
  });
}

function lookForCategoryFormPost() {
  $("#createDiagnosis").submit(function(e) {
    e.preventDefault();

    var $form = $(this);
    var url = "/api/createDiagnosis/";

    var formData = {
      name: document.getElementById("diagnosisName").value,
      categoryName: document.getElementById("categoryName").innerHTML
    };

    $.ajax({
      contentType: "application/json; charset=utf-8",
      dataType: "json",
      type: "POST",
      url: url,
      data: JSON.stringify(formData)
    });
    setTimeout(refreshListOfDiagnoses, 50);
  });
}

var coll = document.getElementsByClassName("collapsible");
var i;

for (i = 0; i < coll.length; i++) {
  coll[i].addEventListener("click", function() {
    this.classList.toggle("active");
    var content = this.nextElementSibling;
    if (content.style.maxHeight) {
      content.style.maxHeight = null;
    } else {
      content.style.maxHeight = content.scrollHeight + "px";
    }
  });
}
