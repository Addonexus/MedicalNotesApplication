let splitUrl = window.location.pathname.split("/");
let caseID = splitUrl[splitUrl.length - 1];
let hiddenParam = document.getElementById("hiddenFormBoolean");
let old_state = [];

$(document).ready(function () {

  hiddenParamDiagnosisSetup(hiddenParam);

  // checkDiagnosisCheckBoxPressed();
  checkSubmitButtonPressed();




});
function toggleDiagnosisCheckBox() {
  let diagnosisCheckBox = document.getElementById("diagnosis-checkbox");
  let diagnosisInputBox = document.getElementById("diagnosesList");
  if (diagnosisCheckBox.checked) {
    console.log("------CHECKBOX IS CHECKED");

    let chipsDiv = $(".chips-autocomplete");
    chipsDiv.material_chip('data');
    console.log("CHIPS DEV" + chipsDiv);
    console.log("DATA INSIDE THE CHIPS :", chipsDiv.material_chip('data'));
    old_state = chipsDiv.material_chip('data');
    console.log("SP<ETJOMG", old_state);
    getAllDiagnosisForTags([{ tag: "Unconfirmed" }]);
    diagnosisInputBox.hidden = true;


  }
  else {
    console.log("------CHECKBOX IS NOT CHECKED");

    getAllDiagnosisForTags(old_state);
    setTimeout(function () {
      diagnosisInputBox.hidden = false;
    }, 25);

  }
}
function checkSubmitButtonPressed() {
  $("#submitCases").submit(function (e) {
    e.preventDefault(); // avoid to execute the actual submit of the form.


    let id;
    let url;
    if (hiddenParam) {
      id = caseID;
      url = "/api/editCase";
    } else {
      url = "/api/saveCase";
    }

    let formData = {
      "id": id,
      "name": document.getElementById("name").value,
      "demographics": document.getElementById("demographics").value,
      "ward": document.getElementById("ward").value,
      "diagnosesList": $("#diagnosesList").material_chip("data"),
      "presentingComplaint": document.getElementById("presentingComplaint").value,
      "presentingComplaintHistory": document.getElementById(
        "presentingComplaintHistory"
      ).value,
      "medicalHistory": document.getElementById("medicalHistory").value,
      "drugHistory": document.getElementById("drugHistory").value,
      "allergies": document.getElementById("allergies").value,
      "familyHistory": document.getElementById("familyHistory").value,
      "socialHistory": document.getElementById("socialHistory").value,
      "notes": document.getElementById("notes").value
    };

    swal({
      icon: "success",
      text: "Case created"
    }).then((x) => {
      submitCase(formData, url);
    })


  });
}
function deleteForm() {
  let id = caseID;

  $.ajax({
    contentType: "application/json; charset=utf-8",
    dataType: "json",
    type: "DELETE",
    url: "/api/deleteCase/" + id,
    data: JSON.stringify({ id: id }),
    success: function (data) {
      console.log(
        "Request Status: " +
        data.status +
        " Status Text: " +
        data.statusText +
        " " +
        " Response URL: " +
        data.redirectUrl
      );
      window.location.href = data.redirectUrl;
      swal({
        icon: "success",
        text: "case deleted"
      });
    },
    error: function (e) {
      swal({
        icon: "error",
        text: "Internal error, try again later"
      });
    }
  });
}

function unhideForm() {
  let hiddenParam = document.getElementById("hiddenFormBoolean");
  if (hiddenParam) {
    let docUrl = document.URL;
    console.log("URL OF THE PAGE: " + docUrl);
    document.getElementById("editCase").hidden = true;
    document.getElementById("allFields").disabled = false;
    document.getElementById("submit-button").hidden = false;
  } else {
    console.log("Param doesn't exist, so don't hide any fields");
  }
}
function mapExistingCaseDetailsToFields() {
  let existingDiagnosisData = [];
  $.ajax({
    type: "GET",
    url: "/api/getCaseById/" + caseID,
    // crossDomain: true,
    contentType: "application/json; charset=utf-8",
    dataType: "json",


    success: function (response) {
      console.log(
        "Response" +
        response.caseModel.toString() +
        "STATus: " +
        response.statusText
      );

      console.log("RESPONSE: " + response.diagnoses);
      console.log("CASE RESPONSE: " + response.caseModel.toString());
      for (let i = 0; i < response.diagnoses.length; i++) {
        existingDiagnosisData.push({ tag: response.diagnoses[i].name });
      }
      let form = response.caseModel;
      document.getElementById("name").value = form.name;
      document.getElementById("demographics").value = form.demographics;
      document.getElementById("ward").value = form.ward; document.getElementById("presentingComplaint").value =
        form.presentingComplaint;
      document.getElementById("presentingComplaintHistory").value =
        form.presentingComplaintHistory;
      document.getElementById("medicalHistory").value = form.medicalHistory;
      document.getElementById("drugHistory").value = form.drugHistory;
      document.getElementById("allergies").value = form.allergies;
      document.getElementById("familyHistory").value = form.familyHistory;
      document.getElementById("socialHistory").value = form.socialHistory;
      document.getElementById("notes").value = form.notes;
      console.log(
        "ARRY OF EXSTING DIAGNOSIS: " + JSON.stringify(existingDiagnosisData)
      );
      for (const [key, value] of existingDiagnosisData.entries()) {
        if (value["tag"].toString().trim() === ("Unconfirmed").toString().trim()) {
          document.getElementById("diagnosis-checkbox").checked = true;
          old_state = { tag: "Unconfirmed" };
          toggleDiagnosisCheckBox();

        }
      }
      getAllDiagnosisForTags(existingDiagnosisData);
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
function submitCase(formData, url) {
  let $form = $(this);
  $.ajax({
    contentType: "application/json; charset=utf-8",
    dataType: "json",
    type: "POST",
    url: url,
    data: JSON.stringify(formData),

    success: function (data) {
      console.log(
        "Request Status: " +
        data.status +
        " Status Text: " +
        data.statusText +
        " " +
        " Response URL: " +
        data.redirectUrl
      );

      $form.find(".error").empty();
      window.location.href = data.redirectUrl;
    },
    error: function (e) {
      $form.find(".error").empty();
      console.log(
        "Request Status: " +
        e.status +
        " Status Text: " +
        e.statusText +
        " " +
        " Response Text: " +
        e.responseText
      );
      let obj = JSON.parse(e.responseText);
      for (i = 0; i < obj.result.length; i++) {
        let item = obj.result[i];
        let field = document.getElementsByClassName(item.fieldName)[0];
        field.innerHTML = item.errorMessage;
      }
    }
  });
}
function checkIfDiagnosisIsPassedAsUnconfirmed() {
  let chipsDiv = $(".chips-autocomplete");
  let chips_data = chipsDiv.material_chip('data');
}
function getAllDiagnosisForTags(existingDiagnosisData) {
  let data = {};
  $.ajax({
    type: "GET",
    url: "http://localhost:8080/api/getAllDiagnosis",
    crossDomain: true,

    success: function (response) {
      let diagnosisArray = response;
      for (let i = 0; i < diagnosisArray.length; i++) {
        data[diagnosisArray[i].name] = null;
      }
      let chipsDiv = $(".chips-autocomplete");
      chipsDiv.material_chip({
        data: existingDiagnosisData,
        autocompleteData: data
      });
      $(".chip").addClass("blue lighten-3");
    }
  });
}
function hiddenParamDiagnosisSetup(hiddenParam) {
  let existingDiagnosisData = [];


  if (hiddenParam) {
    document.getElementById("allFields").disabled = true;
    document.getElementById("submit-button").innerText = "Save Case";
    document.getElementById("title").innerText = "Edit Case";
    document.getElementById("submit-button").hidden = true;

    console.log("PASSED ID: " + caseID);

    mapExistingCaseDetailsToFields(existingDiagnosisData);

  }
  else {
    let passedDiagnosis = document.getElementById("passedDiagnosis");
    if (passedDiagnosis) {
      existingDiagnosisData.push({ tag: passedDiagnosis.value });
    }

    getAllDiagnosisForTags(existingDiagnosisData);
  }

}

console.log($(".chip"));


