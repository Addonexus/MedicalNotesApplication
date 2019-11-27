$(document).ready(function() {

        $(".chips-initial").material_chip();
        var data = {};

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
                    autocompleteData: data
                });
            }
        });




        $("#submitCases").submit(function(e) {
            console.log("hi");
            e.preventDefault(); // avoid to execute the actual submit of the form.

            var $form = $(this);
            var categoryID = document.getElementById("categoryId").value;
            var url = "/api/saveCase/" + categoryID;
            console.log("PASSED URK: " + url);

            var formData = {
                "name" : document.getElementById("name").value,
                "demographics" : document.getElementById("demographics").value,
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

            // formData['diagnoses'] = $('#diagnoses').material_chip('data');

            // console.log(url);
            // console.log(formData);
            console.log("NICE: "+JSON.stringify(formData));


            $.ajax({
                contentType : 'application/json; charset=utf-8',
                dataType : 'json',
                type: "POST",
                url: url,
                // crossDomain: true,
                // data:JSON.stringify($('#diagnoses').material_chip('data')),
                data:JSON.stringify(formData),

                success: function(data) {
                console.log('Request Status: ' + data.status + ' Status Text: ' + data.statusText + ' ' + ' Response URL: ' + data.redirectUrl);
                    $form.find('.error').empty();
//                    var obj = JSON.parse(data.redrectUrl);
//                    console.log("REDIRECT URL: " + obj.redirectUrl + "REDIRECT: " + obj);
                    window.location.href = data.redirectUrl;
                    // alert('HERE' + data); // show response from the php script.
                    console.log("SUCESS" +data);
                },
                error : function(e) {
                    $form.find('.error').empty();
                    console.log('Request Status: ' + e.status + ' Status Text: ' + e.statusText + ' ' + ' Response Text: ' + e.responseText);
                    var obj = JSON.parse(e.responseText);
                    for (i = 0; i < obj.result.length; i++) {
                        var item = obj.result[i];
                        console.log("LOGGED FIELD NAME: " + item.fieldName);
                        console.log("LOGGED Message: " + item.errorMessage);
                        var field = document.getElementsByClassName(item.fieldName)[0];
                        console.log("FIELD " + field);
                        field.innerHTML = item.errorMessage;

                    }
                    console.log("PARSED obj: " + obj.result.length);

                }
            });
        });
    });

function unhideForm(){
    console.log("TSETING BUTTON CLICK EDIT")
    var hiddenParam = document.getElementById("hiddenFormBoolean");
    if(hiddenParam){
        console.log("Param Exists, so hide all input fields")
    //    toggle hide
    //    toggle save to hide

        var docUrl = document.URL;
        console.log("URL OF THE PAGE: " + docUrl);
    }else{
        console.log("Param doesn't exist, so don't hide any fields")
    }
}
