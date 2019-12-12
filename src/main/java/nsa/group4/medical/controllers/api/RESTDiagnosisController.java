package nsa.group4.medical.controllers.api;

import lombok.extern.slf4j.Slf4j;
import nsa.group4.medical.Helper.Helpers;
import nsa.group4.medical.data.DiagnosisInformationRepositoryJDBC;
import nsa.group4.medical.data.DiagnosisRepositoryJPA;
import nsa.group4.medical.domains.*;
import nsa.group4.medical.service.events.DiagnosisInformationAdded;
import nsa.group4.medical.service.implementations.CaseServiceInterface;
import nsa.group4.medical.service.implementations.CategoryServiceInterface;
import nsa.group4.medical.service.implementations.DiagnosisServiceInterface;
import nsa.group4.medical.service.implementations.NotificationServiceInterface;
import nsa.group4.medical.web.CaseForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api")
@Slf4j
public class RESTDiagnosisController {

    private CaseServiceInterface caseServiceInterface;
    private CategoryServiceInterface categoryService;
    private DiagnosisServiceInterface diagnosisService;
//    private NotificationRepositoryJDBC notificationRepositoryJDBC;
    private NotificationServiceInterface notificationService;
    @Autowired
    private Helpers helpers;

    public RESTDiagnosisController(CaseServiceInterface caseServiceInterface,
                                   CategoryServiceInterface categoryService,
                                   DiagnosisServiceInterface diagnosisService,
                                   NotificationServiceInterface notificationService
                          ){
        this.caseServiceInterface =caseServiceInterface;
        this.categoryService=categoryService;
        this.diagnosisService=diagnosisService;
//        this.notificationRepositoryJDBC = notificationRepositoryJDBC;
        this.notificationService = notificationService;
    }

    @DeleteMapping("/deleteDiagnosis/{index}")
    public @ResponseBody ResponseEntity<?> deleteDiagnosisById(
            @PathVariable Long index, HttpServletRequest request
    ){
        Optional<Diagnosis> returnedDiagnosis = diagnosisService.getByDiagnosisId(index);

        AjaxResponseBody response = new AjaxResponseBody();
        if(returnedDiagnosis.isPresent())
        {
            diagnosisService.deleteDiagnosisById(returnedDiagnosis.get().getId());
            caseServiceInterface.checkEmptyDiagnosis();
            response.setStatus("SUCCESS");
            response.setRedirectUrl("/home");
            return ResponseEntity.ok().body(response);
        }
        response.setStatus("FAILURE");
        return ResponseEntity.badRequest().body(response);
    }



    @GetMapping("/getDiagnosisByCategoryId/{index}")
    public @ResponseBody  List<Diagnosis> getDiagnoses(@PathVariable Long index){
        log.debug("REST API RETURN: ");
        Categories categories = categoryService.findById(index).get();
        List<Diagnosis> returnedList = diagnosisService.findByCategories(categories);
//        log.debug("Returned LIST: "+returnedList);
        return returnedList;
    }

    @PostMapping(value = "/createDiagnosis", produces = "application/json")
    public @ResponseBody ResponseEntity<?> saveDiagnosis(@RequestBody Map<String, String> formData, Errors bindingResult) {
        System.out.println(formData.get("name"));
        System.out.println(formData.get("categoryName"));

        Boolean existsAlready = false;

        for (Diagnosis diagnosis : diagnosisService.findAll()) {
            if (diagnosis.getName().equals(formData.get("name"))) {
                existsAlready = true;
            }
        }

        if (existsAlready) {

            AjaxResponseBody responseBody = new AjaxResponseBody();
            responseBody.setDiagnoses(diagnosisService.findAll());
            responseBody.setStatus("SUCCESS");
            return ResponseEntity.badRequest().body(responseBody);
        } else {

            Diagnosis createdDiagnosis = diagnosisService.createDiagnosis(new Diagnosis(
                    helpers.getUserId(),
                    formData.get("name"),
                    categoryService.findByName(formData.get("categoryName")).get()
            ));

            notificationService.saveNotification(new Notifications(helpers.getUserId(),createdDiagnosis));

            AjaxResponseBody responseBody = new AjaxResponseBody();
            responseBody.setDiagnoses(diagnosisService.findAll());
            responseBody.setStatus("SUCCESS");
            return ResponseEntity.ok().body(responseBody);
        }
    }

    @PostMapping(value = "/updateDiagnosis/{index}", produces = "application/json")
    public @ResponseBody ResponseEntity<?> updateDiagnosis(@PathVariable Long index, @RequestBody Map<String, String> formData, Errors bindingResult) {
        String newName = formData.get("newName");
        String newCategory = formData.get("newCategory");
        AjaxResponseBody responseBody = new AjaxResponseBody();
        Optional<Diagnosis> returnedDiagnosis = diagnosisService.getByDiagnosisId(index);
        if (returnedDiagnosis.isPresent()){
            Diagnosis diagnosis = returnedDiagnosis.get();
            // the new diagnosis name has a name that already exists inside the database
            if(!diagnosis.getName().toLowerCase().equals(newName)){

                Optional<Diagnosis> checkDiagnosisNameAlreadyExists = diagnosisService.findByName(newName);
                if(checkDiagnosisNameAlreadyExists.isPresent()){
                    log.debug("DIAGNOSIS NAME ALREADY EXISTS----");
                    // return a bad response indicating that the update didn't go through due to the name already existing
                    responseBody.setStatus("NAME EXISTS");
                    return ResponseEntity.badRequest().body(responseBody);
                }
            }
            log.debug("RUNS THIS TO SET THE NAME ND MOVE TO A CATEGORY----");
            //TODO: creates a new diagnosis if the with the new name if it is different without deleting the old one
            // if also moved to a new category

            // if the new name doesn't already exist in the database then the new details is changed and diagnosis object is saved
            Categories category = categoryService.findByName(newCategory).get();
            System.out.println(category);

            diagnosis.setCategories(category);
            diagnosis.setName(newName);
            diagnosisService.createDiagnosis(diagnosis);

//                AjaxResponseBody responseBody = new AjaxResponseBody();
            responseBody.setStatus("SUCCESS");
            return ResponseEntity.ok().body(responseBody);

        }
        // something went very wrong when updating the diagnosis
        log.debug("FAT ERROR HAS OCCURED----");
        responseBody.setStatus("FAILURE");
        return ResponseEntity.ok().body(responseBody);


    }

    @GetMapping("/getAllDiagnosis")
    public @ResponseBody List<Diagnosis> getDiagnoses(){
            log.debug("REST API RETURN: ");
            List<Diagnosis> returnedList = diagnosisService.findAll();

            log.debug("Returned LIST: "+returnedList);
            return returnedList;

        }
}

