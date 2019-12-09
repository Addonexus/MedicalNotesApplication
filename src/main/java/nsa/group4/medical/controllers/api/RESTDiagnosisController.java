package nsa.group4.medical.controllers.api;

import lombok.extern.slf4j.Slf4j;
import nsa.group4.medical.data.DiagnosisInformationRepositoryJDBC;
import nsa.group4.medical.data.DiagnosisRepositoryJPA;
import nsa.group4.medical.domains.*;
import nsa.group4.medical.service.events.DiagnosisInformationAdded;
import nsa.group4.medical.service.implementations.CaseServiceInterface;
import nsa.group4.medical.service.implementations.CategoryServiceInterface;
import nsa.group4.medical.service.implementations.DiagnosisServiceInterface;
import nsa.group4.medical.service.implementations.NotificationServiceInterface;
import nsa.group4.medical.web.CaseForm;
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
        Diagnosis createdDiagnosis = diagnosisService.createDiagnosis(new Diagnosis(
                                        formData.get("name"),
                                        categoryService.findByName(formData.get("categoryName")).get()
                                        ));

        notificationService.saveNotification(new Notifications(createdDiagnosis));

        AjaxResponseBody responseBody = new AjaxResponseBody();
        responseBody.setDiagnoses(diagnosisService.findAll());
        responseBody.setStatus("SUCCESS");
        return ResponseEntity.ok().body(responseBody);
    }

    @PostMapping(value = "/updateDiagnosis/{index}", produces = "application/json")
    public void updateDiagnosis(@PathVariable Long index, @RequestBody Map<String, String> formData, Errors bindingResult) {
        System.out.println(index);
        System.out.println(formData.get("newName"));
        String newName = formData.get("newName");
        Diagnosis diagnosis = diagnosisService.getByDiagnosisId(index).get();
        diagnosis.setName(newName);
        diagnosisService.createDiagnosis(diagnosis);
        System.out.println(diagnosis);

//        notificationService.saveNotification(new Notifications(createdDiagnosis));

        AjaxResponseBody responseBody = new AjaxResponseBody();
//        responseBody.setDiagnoses(diagnosisService.findAll());
        responseBody.setStatus("SUCCESS");
    }

    @GetMapping("/getAllDiagnosis")
    public @ResponseBody List<Diagnosis> getDiagnoses(){
            log.debug("REST API RETURN: ");
            List<Diagnosis> returnedList = diagnosisService.findAll();

            log.debug("Returned LIST: "+returnedList);
            return returnedList;

        }
}

