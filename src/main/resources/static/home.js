$('.modal').modal();


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
  refreshListOfDiagnoses();

  // Check if category form is posted
  lookForCategoryFormPost();
}

if (windowUrl.pathname.includes("home")) {
  refreshListOfCategories();
  showRecentCases = true;
  createCategory();
}

if (windowUrl.pathname.includes("diagnosis")) {
  showRecentCases = true;
  markNotificationRead();
  getDiagnosisInformation();
  postDiagnosisInfo();
  getFreehandNotes();
  postFreehandNote();
}

if (windowUrl.pathname.includes("case")) {
  showRecentCases = true;
}

if (showRecentCases) {
  getRecentCases();
}

function postDiagnosisInfo() {
  console.log("--- post diagnosis info function --- ");
  $("#createDiagnosisInfo").submit(function (e) {
    e.preventDefault();
    console.log("--- posting diagnosis info --- ");

    var $form = $(this);
    var url = "/api/createDiagnosisInformation";

    var formData = {
      diagnosisId: lastInt,
      key: document.getElementById("DiagnosisInfoKey").value,
      value: document.getElementById("DiagnosisInfoValue").value
    };

    console.log(formData);

    $.ajax({
      contentType: "application/json; charset=utf-8",
      dataType: "json",
      type: "POST",
      url: url,
      data: JSON.stringify(formData),
      success: function (json) {
        $("#diaInfoTable tr").each(function () {
          // console.log(this.id);
          if (this.id) {
            this.remove();
          }
          document.getElementsByClassName("content")[0].style.maxHeight =
            "none";
          // $(this)
          //   .find("td")
          //   .each(function() {
          //     // console.log("hiya");
          //   });
        });
        getDiagnosisInformation();
      },
      error: function (json) {
        // alert("error!");
        console.log("error-h.html");
      }
    });
  });
}

function postFreehandNote() {
  console.log("--- post freehand notes function --- ");
  $("#createFreehandNotes").submit(function (e) {
    e.preventDefault();
    console.log("--- posting freehand notes --- ");

    var $form = $(this);
    var url = "/api/createFreehandNotes";

    var formData = {
      diagnosisId: lastInt,
      field: document.getElementById("FreehandNotesField").value
    };

    console.log(formData);

    $.ajax({
      contentType: "application/json; charset=utf-8",
      dataType: "json",
      type: "POST",
      url: url,
      data: JSON.stringify(formData),
      success: function (json) {
        $("#freehandNotesTable tr").each(function () {
          if (this.id) {
            this.remove();
          }
          document.getElementsByClassName("content")[0].style.maxHeight =
            "none";
        });
        getFreehandNotes();
      },
      error: function (json) {
        console.log("error-h.html");
      }
    });
  });
}

function refreshListOfDiagnoses() {
  var grid = document.getElementById("content-grid");

  while (grid.firstChild) {
    grid.removeChild(grid.firstChild);
  }
  console.log(window.location);

  container = document.getElementById("modal-container");
  console.log("container");
  console.log(container);
  container.innerHTML = "";
  var hmm;

  if (window.location.pathname.includes("category")) {
    $.get("/api/getDiagnosisByCategoryId/" + lastInt, function (data) {
      $(".result").html(data);
      console.log(data);
      var grid = document.getElementById("content-grid");
      for (i = 0; i < data.length; i++) {
        console.log("diagnosis:" + data[i]);
        var a = document.createElement("a");
        var b = document.createElement("button");
        a.setAttribute("href", "/diagnosis/" + data[i].id);
        a.style.boxShadow = "0px 5px lightblue"
        a.style.width = "85%";
        b.appendChild(document.createTextNode(data[i].name));
        b.setAttribute("class", "btn content-item bigger");

        b.style.fontWeight = "600";
        a.appendChild(b);

        grid.appendChild(a);

        var customId = "deleteButton" + data[i].id;

        var settings = document.createElement("div");
        var settingsButton = document.createElement("button");
        var settingsIcon = document.createElement("i");

        settings.style.width = "15%";
        settings.style.boxShadow = "0px 5px lightblue"
        settingsIcon.appendChild(document.createTextNode("edit"));
        settingsIcon.setAttribute("class", "material-icons large");

        settingsButton.appendChild(settingsIcon);
        settingsButton.setAttribute(
          "class",
          "btn content-item bigger modal-trigger"
        );
        settingsButton.setAttribute("href", "#diaModal" + i);

        settingsButton.style.backgroundColor = "#eeeeff";
        settingsButton.style.borderRadius = "5px";
        settingsButton.style.borderLeft = "1px solid grey";
        settingsButton.style.fontWeight = "600";

        settings.appendChild(settingsButton);
        grid.appendChild(settings);

        // Modal stuff
        diagnosisTitle = document.createElement("input");
        diagnosisTitle.id = "modalDiagnosisTitle" + data[i].id;
        diagnosisTitle.placeholder = data[i].name;

        inputSelect = document.createElement('select');
        inputSelect.setAttribute("class", "browser-default");
        inputSelect.id = "modalDiagnosisCategoryTitle" + data[i].id;

        diagnosisCategoryTitle = document.createElement("div");
        diagnosisCategoryTitle.setAttribute("class", "input-field col s12");
        diagnosisCategoryTitle.appendChild(inputSelect);

        console.log(data[i].categories);
        diagnosisCategoryTitle.placeholder = data[i].categories.name;

        // Modal content
        modalContent = document.createElement("div");
        modalContent.setAttribute("class", "modal-content");
        modalContent.style.padding = "30px";
        modalContent.appendChild(diagnosisTitle);
        modalContent.appendChild(diagnosisCategoryTitle);

        // Modal footer
        saveButton = document.createElement("button");
        saveButton.setAttribute("class", "btn-small white black-text");
        saveButton.setAttribute(
          "onClick",
          "updateDiagnosis(" + data[i].id + ", diaModal" + i + ")"
        );

        saveButton.appendChild(document.createTextNode("save"));

        deleteButton = document.createElement("button");
        deleteButton.setAttribute("class", "btn-small white-text");
        deleteButton.setAttribute(
          "onClick",
          "deleteDiagnosis(" + data[i].id + ", diaModal" + i + ")"
        );
        deleteButton.appendChild(document.createTextNode("delete"));
        deleteButton.style.marginLeft = "20px";
        deleteButton.style.backgroundColor = "#ff5555";
        deleteButton.style.fontWeight = "600";
        deleteButton.style.color = "white";


        modalFooter = document.createElement("div");
        modalFooter.setAttribute("class", "modal-footer");
        modalFooter.appendChild(saveButton);
        modalFooter.appendChild(deleteButton);
        modalFooter.style.borderTop = "2px solid black";

        modal = document.createElement("div");
        modal.setAttribute("class", "modal");
        modal.setAttribute("id", "diaModal" + i);
        modal.appendChild(modalContent);
        modal.appendChild(modalFooter);

        container.appendChild(modal);

        console.log(modal);
      }
      doThis(data);

      $('.modal').modal();
    });
  }
}

function doThis(data) {
  $.get("/api/getAllCategories").then(function (categories) {
    console.log(data);
    for (var i = 0; i < data.length; i++) {
      inputSelectRef = document.getElementById("modalDiagnosisCategoryTitle" + data[i].id);
      console.log(inputSelectRef);
      for (var j = 0; j < categories.length; j++) {
        inputOption = document.createElement('option');
        inputOption.setAttribute("value", categories[j].name);
        inputOption.appendChild(document.createTextNode(categories[j].name));

        if (data[i].categories.name == categories[j].name) {
          inputOption.setAttribute("selected", "selected");
        }
        inputSelectRef.appendChild(inputOption)



      }
    }
    console.log(categories);
  });
}

function updateCategory(num, modal) {
  var categoryModalTitle = document.getElementById("modalCategoryTitle" + num);
  //checks to see if the user entered anything in the diagnosis title field
  if (categoryModalTitle.value == "") {
    //  sets the new name as whatever the current name of the diagnosis is
    newName = categoryModalTitle.placeholder;
  }
  else {
    // otherwise sets the new diagnosis name as title entered by the user
    newName = categoryModalTitle.value;
  }
  // instance.close();
  var formData = {
    "newName": newName
  };

  $.ajax({
    contentType: "application/json; charset=utf-8",
    dataType: "json",
    data: JSON.stringify(formData),
    type: "POST",
    url: "/api/updateCategory/" + num,
    success: function (json) {
      getRecentCases();
      refreshNotifications();
      console.log("ERROR");
      refreshListOfCategories();
      swal({
        title: "Success",
        text: "Category Moved/Edited",
        icon: "success",
      });
    },
    error: function (response) {
      //        if(response.status == "NAME EXISTS"){

      var obj = JSON.parse(response.responseText);
      if (obj.status = "NAME EXISTS") {
        swal({
          title: "Error",
          text: "Category Name already exists, please try a different name",
          icon: "error",
        });
      }
      console.log("ERROR");
    }
    //      refreshListOfCategories();
  });
}

function updateDiagnosis(num, modal) {
  var diagnosisModalTitle = document.getElementById("modalDiagnosisTitle" + num);
  //checks to see if the user entered anything in the diagnosis title field
  if (diagnosisModalTitle.value == "") {
    //  sets the new name as whatever the current name of the diagnosis is
    newName = diagnosisModalTitle.placeholder;
  }
  else {
    // otherwise sets the new diagnosis name as title entered by the user
    newName = diagnosisModalTitle.value;
  }
  newCategory = document.getElementById("modalDiagnosisCategoryTitle" + num).value;
  // instance.close();
  var formData = {
    "newName": newName,
    "newCategory": newCategory
  };

  $.ajax({
    contentType: "application/json; charset=utf-8",
    dataType: "json",
    data: JSON.stringify(formData),
    type: "POST",
    url: "/api/updateDiagnosis/" + num,
    success: function (json) {
      getRecentCases();
      refreshNotifications();
      console.log("ERROR");
      refreshListOfDiagnoses();
      swal({
        title: "Success",
        text: "Diagnosis Moved/Edited",
        icon: "success",
      });
    },
    error: function (response) {
      var obj = JSON.parse(response.responseText);
      if (obj.status = "NAME EXISTS") {
        swal({
          title: "Error",
          text: "Diagnosis Name already exists, please try a different name",
          icon: "error",
        });
        //        alert("Diagnosis Name already exists, please try a different name")
      }
      console.log("ERROR");
    }
  });
}

function deleteDiagnosis(num, modal) {
  swal("Hello world!");
  console.log(num);
  console.log("my modal: " + modal);

  swal({
    title: "Are you sure?",
    text: "You will lose all of the cases attributed to this diagnosis",
    icon: "warning",
    buttons: true,
    dangerMode: true,
  })
    .then((willDelete) => {
      if (willDelete) {
        $.ajax({
          contentType: "application/json; charset=utf-8",
          dataType: "json",
          type: "DELETE",
          url: "/api/deleteDiagnosis/" + num,
          success: function (json) {
            getRecentCases();
            refreshNotifications();
            refreshListOfDiagnoses();
          },
          error: function (json) {
            console.log("ERROR");
          }
        });
        swal("Diagnosis deleted", {
          icon: "success",
        });
      }
    });
}

function reload() {
  document.location.reload();
}
function deleteCategory(num) {
  swal({
    title: "Are you sure?",
    text: "You will lose all of the cases / diagnoses attributed to this category",
    icon: "warning",
    buttons: true,
    dangerMode: true,
  })
    .then((willDelete) => {
      if (willDelete) {
        console.log(num);
        var formData = {
          id: num
        };

        $.ajax({
          contentType: "application/json; charset=utf-8",
          dataType: "json",
          type: "DELETE",
          url: "/api/deleteCategory/" + num,
          //        data: JSON.stringify(formData),
          success: function (json) {
            // alert("Worked!");
            refreshListOfCategories();
            //                var listOfCases = document.getElementById("recentCases");
            //
            //                listOfCases.empty();
            getRecentCases();
          },
          error: function (json) {
            // alert("error!");
            console.log("ERROR");
          }
        });
        swal("Category deleted", {
          icon: "success",
        });
      }
    });
}

function refreshListOfCategories() {
  var grid = document.getElementById("content-grid");

  while (grid.firstChild) {
    grid.removeChild(grid.firstChild);
  }
  console.log(window.location);
  container = document.getElementById("modal-container");
  console.log("container");
  console.log(container);
  container.innerHTML = "";

  $.get("/api/getAllCategories/", function (data) {
    $(".result").html(data);

    var grid = document.getElementById("content-grid");

    if (data.length == 0) {
      h4 = document.createElement("h4");
      h4.appendChild(document.createTextNode("No categories exist yet"))
      grid.appendChild(h4);
    }

    console.log("home data");
    console.log(data);
    for (i = 0; i < data.length; i++) {
      console.log("categories:" + data[i]);
      var a = document.createElement("a");
      var b = document.createElement("button");
      a.setAttribute("href", "/category/" + data[i].id);
      a.style.width = "85%";
      a.style.boxShadow = "0px 5px lightblue"
      b.appendChild(document.createTextNode(data[i].name));
      b.setAttribute("class", "btn content-item bigger");
      b.style.fontWeight = "600";
      a.appendChild(b);

      var customId = "deleteButton" + data[i].id;

      var settings = document.createElement("a");
      var settingsButton = document.createElement("button");
      var settingsIcon = document.createElement("i");

      settings.style.width = "15%";
      settings.style.boxShadow = "0px 5px lightblue"

      settingsIcon.appendChild(document.createTextNode("edit"));
      settingsIcon.setAttribute("class", "material-icons large");

      settingsButton.appendChild(settingsIcon);
      settingsButton.setAttribute("class", "btn content-item bigger modal-trigger");
      settingsButton.setAttribute("href", "#categoryModal" + i);
      //      settingsButton.setAttribute(
      //        "onClick",
      //        "deleteCategory(" + data[i].id + ")"
      //      );

      // settingsButton.style.backgroundColor="#eeeeff";
      settingsButton.style.backgroundColor = "#eeeeff";
      settingsButton.style.borderRadius = "5px";
      settingsButton.style.borderLeft = "1px solid grey";
      settingsButton.style.fontWeight = "600";
      settingsButton.id = customId;

      settings.appendChild(settingsButton);

      if (data[i].name == "Miscellaneous") {
        grid.insertBefore(settings, grid.firstChild);
        grid.insertBefore(a, grid.firstChild);
      } else {
        grid.appendChild(a);
        grid.appendChild(settings);
      }

      // Modal Form Setup
      categoryTitle = document.createElement("input");
      categoryTitle.id = "modalCategoryTitle" + data[i].id;
      categoryTitle.placeholder = data[i].name;

      // Modal content
      modalContent = document.createElement("div");
      modalContent.setAttribute("class", "modal-content");
      modalContent.style.padding = "30px";
      modalContent.appendChild(categoryTitle);


      // Modal footer content
      // Save Button
      saveButton = document.createElement("button");
      saveButton.setAttribute("class", "btn-small white black-text");
      saveButton.setAttribute(
        "onClick",
        "updateCategory(" + data[i].id + ", categoryModal" + i + ")"
      );
      saveButton.appendChild(document.createTextNode("save"));

      // Delete Button
      deleteButton = document.createElement("button");
      deleteButton.setAttribute("class", "btn-small white-text");
      deleteButton.setAttribute(
        "onClick",
        "deleteCategory(" + data[i].id + ", categoryModal" + i + ")"
      );
      deleteButton.appendChild(document.createTextNode("delete"));
      deleteButton.style.marginLeft = "20px";
      deleteButton.style.backgroundColor = "#ff5555";
      deleteButton.style.fontWeight = "600";



      modalFooter = document.createElement("div");
      modalFooter.setAttribute("class", "modal-footer");
      modalFooter.appendChild(saveButton);
      modalFooter.appendChild(deleteButton);
      modalFooter.style.borderTop = "2px solid black";

      modal = document.createElement("div");
      modal.setAttribute("class", "modal");
      modal.setAttribute("id", "categoryModal" + i);
      modal.appendChild(modalContent);
      modal.appendChild(modalFooter);

      container.appendChild(modal);

      console.log(modal);
    }
    $('.modal').modal();
  });
}

function getFreehandNotes() {
  $.get("/api/returnedFreehandNotes/" + lastInt, function (data) {
    $(".result").html(data);
    console.log(data);
    var freehandNotesTable = document.getElementById("freehandNotesTable");
    for (i = 0; i < data.length; i++) {
      var row = freehandNotesTable.insertRow(
        freehandNotesTable.rows.length - 1
      );
      row.id = "hi";
      var field = row.insertCell(-1);

      field.innerHTML = data[i].field;
    }
  });
}

function getDiagnosisInformation() {
  $.get("/api/returnedDiagnosisInfo/" + lastInt, function (data) {
    $(".result").html(data);
    console.log(data);
    var diagnosisInfoTable = document.getElementById("diaInfoTable");
    for (i = 0; i < data.length; i++) {
      var row = diagnosisInfoTable.insertRow(
        diagnosisInfoTable.rows.length - 1
      );
      row.id = "hi";
      var key = row.insertCell(-1);
      var value = row.insertCell(-1);

      key.innerHTML = data[i].field;
      value.innerHTML = data[i].value;
    }
  });
}

function getRecentCases() {
  var listOfCases = document.getElementById("recentCases");
  while (listOfCases.hasChildNodes()) {
    listOfCases.removeChild(listOfCases.childNodes[0]);
  }
  $.ajax({
    type: "GET",
    url: "/api/getRecentCases",
    // crossDomain: true,
    contentType: "application/json; charset=utf-8",
    dataType: "json",

    success: function (response) {
      var ids = response.categoryIds;
      var cases = response.casesList;
      recentCases = document.getElementById("recentCases");
      noCasesH5 = document.getElementById("no-cases-h5");
      recentCases.style.height = "1000px";


      if (cases.length == 0) {
        noCasesH5.style.visibility = "visible";
        recentCases.style.visibility = "hidden";

      } else {
        recentCases.style.visibility = "visible";
        noCasesH5.style.visibility = "hidden";


      }

      //      listOfCases.empty();
      for (i = 0; i < cases.length; i++) {
        var li = document.createElement("a");
        li.appendChild(document.createTextNode(cases[i].name));
        li.setAttribute("id", cases[i].id);
        li.setAttribute("class", "collection-item");
        li.setAttribute("href", "/case/" + cases[i].id);
        listOfCases.appendChild(li);
      }
    },
    error: function (response) {
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

function createCategory() {
  $("#createCategory").submit(function (e) {
    e.preventDefault();

    var $form = $(this);
    var url = "/api/createCategory/";


    var formData = {
      name: document.getElementById("categoryName").value
    };

    $.ajax({
      contentType: "application/json; charset=utf-8",
      dataType: "json",
      type: "POST",
      url: url,
      data: JSON.stringify(formData),
      success: function (json) {
        refreshListOfCategories();
        swal({
          title: "Success",
          text: "Category Created",
          icon: "success",
        });
      },
      error: function (json) {
        swal({
          title: "Error",
          text: "Category Name already exists, please try a different name",
          icon: "error",
        });
      }
    });
  });
}

function lookForCategoryFormPost() {
  $("#createDiagnosis").submit(function (e) {
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
      data: JSON.stringify(formData),
      success: function (json) {
        refreshListOfDiagnoses();
        swal({
          title: "Success",
          text: "Diagnosis Created",
          icon: "success",
        });
      },
      error: function (json) {
        swal({
          title: "Error",
          text: "Diagnosis Name already exists, please try a different name",
          icon: "error",
        });
      }
    });
  });
}

var coll = document.getElementsByClassName("collapsible");
var i;

for (i = 0; i < coll.length; i++) {
  coll[i].addEventListener("click", function () {
    this.classList.toggle("active");
    var content = this.nextElementSibling;
    if (content.style.maxHeight) {
      content.style.maxHeight = null;
    } else {
      content.style.maxHeight = content.scrollHeight + "px";
    }
  });
}


function markNotificationRead() { }
