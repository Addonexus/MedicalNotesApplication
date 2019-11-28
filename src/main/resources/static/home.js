// Get the window URL
windowUrl = window.location;
console.log("Window URL: " + windowUrl);

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
  lastInt = window.location.pathname[window.location.pathname.length - 1];
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

$.get(
  "/api/returnedDiagnosisInfo/" +
    window.location.pathname[window.location.pathname.length - 1],
  function(data) {
    $(".result").html(data);
    console.log(data);
    var diagnosisInfoTable = document.getElementById("diaInfoTable");
    for (i = 0; i < data.length; i++) {
      var row = diagnosisInfoTable.insertRow(
        diagnosisInfoTable.rows.length - 1
      );
      var key = row.insertCell(-1);
      var value = row.insertCell(-1);

      key.innerHTML = data[i].key;
      value.innerHTML = data[i].value;
    }
  }
);

function getRecentCases() {
  $.get("/recentCases/", function(data) {
    $(".result").html(data);
    console.log(data);
    var listOfCases = document.getElementById("recentCases");
    for (i = 0; i < data.length; i++) {
      var li = document.createElement("a");
      li.appendChild(document.createTextNode(data[i].name));
      li.setAttribute("id", data[i].id);
      li.setAttribute("class", "collection-item");
      li.setAttribute("href", "/case/" + data[i].id);
      listOfCases.appendChild(li);
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
