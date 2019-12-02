package nsa.group4.medical.controllers.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import nsa.group4.medical.data.CategoriesRepositoryJPA;
import nsa.group4.medical.data.DiagnosisInformationRepositoryJDBC;
import nsa.group4.medical.data.DiagnosisRepositoryJPA;
import nsa.group4.medical.domains.CaseModel;
import nsa.group4.medical.domains.Categories;
import nsa.group4.medical.domains.Diagnosis;
import nsa.group4.medical.domains.DiagnosisInformation;
import nsa.group4.medical.service.CaseService;
import nsa.group4.medical.service.CaseServiceInterface;
import nsa.group4.medical.service.DiagnosisService;
import nsa.group4.medical.service.DiagnosisServiceInterface;
import nsa.group4.medical.web.CaseForm;
import org.apache.catalina.User;
import org.apache.catalina.mapper.Mapper;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.html.Option;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api")
@Slf4j
public class RESTController {

    private CaseServiceInterface caseServiceInterface;
    private CategoriesRepositoryJPA categoriesRepository;
    private DiagnosisServiceInterface diagnosisService;
    private DiagnosisRepositoryJPA diagnosisRepositoryJPA;
    private DiagnosisInformationRepositoryJDBC diagnosisInformationRepositoryJDBC;



    public RESTController(CaseServiceInterface caseServiceInterface,
                          CategoriesRepositoryJPA categoriesRepository,
                          DiagnosisServiceInterface diagnosisService,
                          DiagnosisRepositoryJPA diagnosisRepositoryJPA,
                          DiagnosisInformationRepositoryJDBC diagnosisInformationRepositoryJDBC
                          ){
        this.caseServiceInterface =caseServiceInterface;
        this.categoriesRepository=categoriesRepository;
        this.diagnosisService=diagnosisService;
        this.diagnosisRepositoryJPA = diagnosisRepositoryJPA;
        this.diagnosisInformationRepositoryJDBC = diagnosisInformationRepositoryJDBC;

    }

    @GetMapping("/returnedDiagnosisInfo/{index}")
    public @ResponseBody List<DiagnosisInformation> getDiagnosisInformation(@PathVariable Long index) {
        System.out.println("hmdds");
        return diagnosisInformationRepositoryJDBC.getDiagnosisInformationByDiagnosisId(index);
    }

    @GetMapping("/getCaseById/{index}")
    public @ResponseBody ResponseEntity<?> getCaseById(
            @PathVariable Long index
    ) {
        System.out.println("IOCNOEINICNECIENCINEICICE");
        Optional<CaseModel> ok = caseServiceInterface.findByCaseId(index);
//        System.out.println("case:" + ok.get().toString());
        AjaxResponseBody response = new AjaxResponseBody();
        if(ok.isPresent()){
            CaseModel caseOk = ok.get();
            response.setCaseModel(ok.get());

//            ArrayList<Diagnosis> diagnoses = caseOk.getDiagnosesList().stream().map(x->x.getCases().clear()).collect(Collectors.toList());;
//            response.setDiagnoses(ok.get().getDiagnosesList().stream().map(x->x.setCases(new ArrayList<CaseModel>())).collect());
            response.setDiagnoses(caseOk.getDiagnosesList());
            response.setStatus("SUCCESS");
            return ResponseEntity.ok().body(response);
        }
        response.setStatus("FALSE");
        return ResponseEntity.badRequest().body(response);
    }




    @PostMapping("/deleteCase/{index}")
    public @ResponseBody ResponseEntity<?> deleteCaseById(
            @PathVariable Long index, HttpServletRequest request
    ){
     Optional<CaseModel> returnedCase = caseServiceInterface.findByCaseId(index);

        AjaxResponseBody response = new AjaxResponseBody();
        if(returnedCase.isPresent())
     {
         caseServiceInterface.deleteCaseById(returnedCase.get().getId());
         response.setStatus("SUCCESS");
         response.setRedirectUrl("/home");
         return ResponseEntity.ok().body(response);


     }
        response.setStatus("FALSE");
        return ResponseEntity.badRequest().body(response);
    }


    @GetMapping("/getDiagnosisByCategoryId/{index}")
    public @ResponseBody  List<Diagnosis> getDiagnoses(@PathVariable Long index){
        log.debug("REST API RETURN: ");
        Categories categories = categoriesRepository.findById(index).get();
        List<Diagnosis> returnedList = diagnosisService.findByCategories(categories);
        log.debug("Returned LIST: "+returnedList);
//        for(Diagnosis diagnosis:returnedList){
//            for(CaseModel caseModel: diagnosis.getCases()){
//                caseModel.setDiagnosesList(null);
//            }
//        }
//        returnedList.forEach(x-> x.getCases().forEach(y -> y.setDiagnosesList(new ArrayList<>())));
        return returnedList;
    }

//    @GetMapping("/getDiagnosisByCategoryId")
//    public @ResponseBody

//    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "/editCase", method = POST, produces = "application/json")
    public @ResponseBody ResponseEntity<?> editCase(@Valid @RequestBody CaseForm formData,
                                                    Errors bindingResult) {

        AjaxResponseBody result = new AjaxResponseBody();

        if (bindingResult.hasErrors()) {
            log.debug("BINDING ERROS" + bindingResult.toString());
            //refactoring how error is returned
            result.setStatus("FAIL");
            List<FieldError> allErrors = bindingResult.getFieldErrors();
            List<DiagnosisFieldsError> errorMessages = new ArrayList<>();

            for (FieldError objectError : allErrors) {
                errorMessages.add(new DiagnosisFieldsError(objectError.getField(), objectError.getField() + "  " + objectError.getDefaultMessage()));
            }
            result.setResult(errorMessages);
            log.debug("RETURNED ERRORS: " + result);
            return ResponseEntity.badRequest().body(result);

        } else {
            caseServiceInterface.updateCase(formData);
            result.setStatus("SUCCESS");
            result.setRedirectUrl("/case/"+formData.getId());
            return ResponseEntity.ok().body(result);
        }
    }
    @RequestMapping(value = "/saveCase", method = POST, produces = "application/json")
//    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody ResponseEntity<?> saveCase(@Valid @RequestBody CaseForm formData,
                                                   Errors bindingResult) {
        AjaxResponseBody result = new AjaxResponseBody();

        if (bindingResult.hasErrors()) {
            log.debug("BINDING ERROS" + bindingResult.toString());
            //refactoring how error is returned
            result.setStatus("FAIL");
            List<FieldError> allErrors = bindingResult.getFieldErrors();
            List<DiagnosisFieldsError> errorMessages = new ArrayList<>();

            for (FieldError objectError : allErrors) {
                errorMessages.add(new DiagnosisFieldsError(objectError.getField(), objectError.getField() + "  " + objectError.getDefaultMessage()));
            }
            result.setResult(errorMessages);
            log.debug("RETURNED ERRORS: " + result);
            return ResponseEntity.badRequest().body(result);

        } else {
            caseServiceInterface.createCase(formData);
            result.setStatus("SUCCESS");
            result.setRedirectUrl("/home");
            log.debug("RETURNED SUCEUSS: " + result);
            return ResponseEntity.ok().body(result);
        }
        }


    @PostMapping(value = "/createDiagnosis", produces = "application/json")
    public @ResponseBody String saveDiagnosis(@RequestBody Map<String, String> formData, Errors bindingResult) {
        System.out.println(formData.get("name"));
        System.out.println(formData.get("categoryName"));
        diagnosisService.createDiagnosis(new Diagnosis(
                                        formData.get("name"),
                                        categoriesRepository.findByName(formData.get("categoryName")).get()
                                        ));
        return "NUGGET";
    }

    @GetMapping("/getAllDiagnosis")
    public @ResponseBody List<Diagnosis> getDiagnoses(){
            log.debug("REST API RETURN: ");
            List<Diagnosis> returnedList = diagnosisRepositoryJPA.findAll();

            log.debug("Returned LIST: "+returnedList);
//        for(Diagnosis diagnosis:returnedList){
//            for(CaseModel caseModel: diagnosis.getCases()){
//        }
//        returnedList.forEach(x-> x.getCases().forEach(y -> y.setDiagnosesList(new ArrayList<>())));
            return returnedList;

        }

    @GetMapping("getRecentCases")
    public @ResponseBody List<CaseModel> getRecentCases() {
        return caseServiceInterface.findAllByOrderByCreationDate();
    }

    }

