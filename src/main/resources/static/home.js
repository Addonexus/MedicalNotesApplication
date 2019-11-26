// Get the window URL
windowUrl = window.location;
console.log("Window URL: " + windowUrl);

// Show recent cases false by default
var showRecentCases = false;

if(windowUrl.pathname.includes("category")) {
  showRecentCases = true;
}

$(document).ready(function() {
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

  refreshListOfDiagnoses();
  
  $("#createDiagnosis").submit(function(e) {
    e.preventDefault();

    var $form = $(this);
    var url = "/api/createDiagnosis/";
    
    var formData = {
      "name" : document.getElementById("diagnosisName").value,
      "categoryName" : document.getElementById("categoryName").innerHTML
    }

    $.ajax({
      contentType : 'application/json; charset=utf-8',
      dataType : 'json',
      type : "POST",
      url: url,
      data: JSON.stringify(formData),
    })

    refreshListOfDiagnoses();
    
  })
})

function refreshListOfDiagnoses() {

  var grid = document.getElementById("content-grid");

  while(grid.firstChild){
    grid.removeChild(grid.firstChild);
  }
  console.log("HI THERE");
  console.log(window.location);
  lastInt = window.location.pathname[window.location.pathname.length-1];
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
        b.appendChild(document.createTextNode(data[i].name));
        b.setAttribute("class", "btn content-item")
        a.appendChild(b);
        grid.appendChild(a);
      }
    });
  }
}
