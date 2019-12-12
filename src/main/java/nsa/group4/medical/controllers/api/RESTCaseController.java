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
public class RESTCaseController {

    private CaseServiceInterface caseServiceInterface;

    public RESTCaseController(CaseServiceInterface caseServiceInterface){
        this.caseServiceInterface =caseServiceInterface;
    }

    @GetMapping("/getCaseById/{index}")
    public @ResponseBody ResponseEntity<?> getCaseById(@PathVariable Long index) {
        System.out.println("----REST GET Mapping: /getCaseById/{index} ----");
        Optional<CaseModel> ok = caseServiceInterface.findByCaseId(index);
        AjaxResponseBody response = new AjaxResponseBody();
        if(ok.isPresent()){
            CaseModel caseOk = ok.get();
            response.setCaseModel(ok.get());
            response.setDiagnoses(caseOk.getDiagnosesList());
            response.setStatus("SUCCESS");
            return ResponseEntity.ok().body(response);
        }
        response.setStatus("FALSE");
        return ResponseEntity.badRequest().body(response);
    }

    @DeleteMapping("/deleteCase/{index}")
    public @ResponseBody ResponseEntity<?> deleteCaseById(@PathVariable Long index,
                                                          HttpServletRequest request){
     Optional<CaseModel> returnedCase = caseServiceInterface.findByCaseId(index);

        AjaxResponseBody response = new AjaxResponseBody();
        if(returnedCase.isPresent())
     {
         caseServiceInterface.deleteCaseById(returnedCase.get().getId());
         response.setStatus("SUCCESS");
         response.setRedirectUrl("/home");
         return ResponseEntity.ok().body(response);
     }
        response.setStatus("FAILURE");
        return ResponseEntity.badRequest().body(response);
    }

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

    @GetMapping("/recentCases")
    public @ResponseBody List<CaseModel> getRecentCases() {
        return caseServiceInterface.findAllByOrderByCreationDate();
    }

    @GetMapping("/getRecentCases")
    public @ResponseBody
    ResponseEntity<?> getRecentCasesMain() {
        AjaxResponseBody responseBody = new AjaxResponseBody();
        responseBody.setCasesList(caseServiceInterface.findAllByOrderByCreationDate());
        return ResponseEntity.ok().body(responseBody);
    }

    @PostMapping("/getCasesByDate")
    public @ResponseBody ResponseEntity<?> getCasesByDate(@RequestBody Map<String, String> formData) throws ParseException {
        String date = formData.get("name").replaceAll("[$,]", "");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd yyyy");
        Date myDate = simpleDateFormat.parse(date);

        LocalDateTime ldt = LocalDateTime.ofInstant(myDate.toInstant(), ZoneId.systemDefault());

        AjaxResponseBody responseBody = new AjaxResponseBody();
        System.out.println(ldt);
//        System.out.println(caseServiceInterface.findByCreationDateBetween(ldt.minusDays(1000), ldt.plusDays(1000)));
        responseBody.setCasesList(caseServiceInterface.findByCreationDateBetween(ldt.minusDays(1), ldt.plusDays(1)));
        responseBody.setStatus("SUCCESS");
        return ResponseEntity.ok().body(responseBody);
    }
}

