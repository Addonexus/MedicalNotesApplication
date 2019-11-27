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
                "diagnosesList" : $('#diagnosesList').material_chip('data')
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