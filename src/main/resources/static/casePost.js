var splitUrl = window.location.pathname.split('/');
        var caseID = splitUrl[splitUrl.length-1];
$(document).ready(function() {

    function getAllDiagnosisForTags() {
        $.ajax({
            type: "GET",
            url: "http://localhost:8080/api/getAllDiagnosis",
            crossDomain: true,
    
            success: function(response) {
                var diagnosisArray = response;
                for (var i = 0; i < diagnosisArray.length; i++) {
                    console.log(diagnosisArray[i].name);
                    data[diagnosisArray[i].name] = null;
                }
                $(".chips-autocomplete").material_chip({
                    data: existingDiagnosisData,
                    autocompleteData: data,
                });
                $(".chip").addClass("blue lighten-3");

            }
        });
    }
    var hiddenParam = document.getElementById("hiddenFormBoolean");
        var data = {};
        var existingDiagnosisData = [];

    if(hiddenParam){
    console.log("HIDDENPARAM")
        document.getElementById("allFields").disabled = true;
        document.getElementById("submit-button").innerText ="Save Case";
        document.getElementById("title").innerText ="Edit Case";
        console.log("HDIING SUBMIT BUTTON")
        document.getElementById("submit-button").hidden = true;

        console.log("PASSED ID: " + caseID);

        $.ajax({
            type: "GET",
            url: "/api/getCaseById/" + caseID,
            // crossDomain: true,
            contentType : 'application/json; charset=utf-8',
            dataType : 'json',

            success: function(response) {
                console.log("Response" + response.caseModel.toString() + "STATus: " + response.statusText);

                console.log("RESPONSE: " + response.diagnoses);
                console.log("CASE RESPONSE: " + response.caseModel.toString());
                for (var i = 0; i < response.diagnoses.length; i++) {
                    existingDiagnosisData.push({tag:response.diagnoses[i].name});
                    console.log("ROSIEJRLKESKR" +response.diagnoses[i].name);
                }
                console.log("EXCAPSED");
                var form = response.caseModel;
                document.getElementById('name').value = form.name;
                document.getElementById("demographics").value = form.demographics;
                document.getElementById("ward").value = form.ward;
                document.getElementById("presentingComplaint").value = form.presentingComplaint;
                document.getElementById("presentingComplaintHistory").value = form.presentingComplaintHistory;
                document.getElementById("medicalHistory").value = form.medicalHistory;
                document.getElementById("drugHistory").value = form.drugHistory;
                document.getElementById('allergies').value = form.allergies;
                document.getElementById("familyHistory").value = form.familyHistory;
                document.getElementById("socialHistory").value = form.socialHistory;
                document.getElementById("notes").value = form.notes;
                console.log("ARRY OF EXSTING DIAGNOSIS: " + JSON.stringify(existingDiagnosisData))
                getAllDiagnosisForTags();

            },
            error: function(response){
                console.log('Request Status: ' + response.status + ' Status Text: ' + response.statusText + ' ' + ' Response Text: ' + response.responseText);
            }
            });
        }

        getAllDiagnosisForTags();

        $("#submitCases").submit(function(e) {
            console.log("hi");
            e.preventDefault(); // avoid to execute the actual submit of the form.

            var $form = $(this);
            var id;
            var url;
            if(hiddenParam){
                id = caseID;
                url = "/api/editCase"
            }
            else{
            url = "/api/saveCase"
            }

            var formData = {
                "id" : id,
                "name" : document.getElementById("name").value,
                "demographics" : document.getElementById("demographics").value,
                "ward" : document.getElementById("ward").value,
                "diagnosesList" : $('#diagnosesList').material_chip('data'),
                "presentingComplaint" : document.getElementById("presentingComplaint").value,
                "presentingComplaintHistory" : document.getElementById("presentingComplaintHistory").value,
                "medicalHistory" : document.getElementById("medicalHistory").value,
                "drugHistory" : document.getElementById("drugHistory").value,
                "allergies" : document.getElementById("allergies").value,
                "familyHistory" : document.getElementById("familyHistory").value,
                "socialHistory" : document.getElementById("socialHistory").value,
                "notes" : document.getElementById("notes").value
            };

            console.log("NICE: "+JSON.stringify(formData));


            $.ajax({
                contentType : 'application/json; charset=utf-8',
                dataType : 'json',
                type: "POST",
                url: url,
                data:JSON.stringify(formData),

                success: function(data) {
                console.log('Request Status: ' + data.status + ' Status Text: ' + data.statusText + ' ' + ' Response URL: ' + data.redirectUrl);
                    $form.find('.error').empty();
                    window.location.href = data.redirectUrl;

                },
                error : function(e) {
                    $form.find('.error').empty();
                    console.log('Request Status: ' + e.status + ' Status Text: ' + e.statusText + ' ' + ' Response Text: ' + e.responseText);
                    var obj = JSON.parse(e.responseText);
                    for (i = 0; i < obj.result.length; i++) {
                        var item = obj.result[i];
                        var field = document.getElementsByClassName(item.fieldName)[0];
                        field.innerHTML = item.errorMessage;

                    }

                }
            });
        });
    });
function deleteForm(){
var id = caseID;

$.ajax({
        contentType : 'application/json; charset=utf-8',
        dataType : 'json',
        type: "DELETE",
        url: "/api/deleteCase/"+id,
        data:JSON.stringify({"id": id}),
        success: function(data) {
        console.log('Request Status: ' + data.status + ' Status Text: ' + data.statusText + ' ' + ' Response URL: ' + data.redirectUrl);
            window.location.href = data.redirectUrl;
            alert("Deleted Case");
        },
        error : function(e) {
            alert("Something Went Wrong");
            }
    });
}

function unhideForm(){
    var hiddenParam = document.getElementById("hiddenFormBoolean");
    if(hiddenParam){

        var docUrl = document.URL;
        console.log("URL OF THE PAGE: " + docUrl);
        document.getElementById("editCase").hidden = true;
        document.getElementById("allFields").disabled = false;
        document.getElementById("submit-button").hidden = false;
    }else{
        console.log("Param doesn't exist, so don't hide any fields")
    }
}

console.log($(".chip")); 


