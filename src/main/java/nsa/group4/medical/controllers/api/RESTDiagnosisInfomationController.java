package nsa.group4.medical.controllers.api;

import lombok.extern.slf4j.Slf4j;
import nsa.group4.medical.data.DiagnosisInformationRepositoryJDBC;
import nsa.group4.medical.domains.DiagnosisInformation;
import nsa.group4.medical.domains.Notifications;
import nsa.group4.medical.service.events.DiagnosisInformationAdded;
import nsa.group4.medical.service.implementations.DiagnosisServiceInterface;
import nsa.group4.medical.service.implementations.NotificationServiceInterface;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api")
@Slf4j
public class RESTDiagnosisInfomationController {

    private DiagnosisServiceInterface diagnosisService;
    private DiagnosisInformationRepositoryJDBC diagnosisInformationRepositoryJDBC;
    private NotificationServiceInterface notificationService;


    //Constructor call to retrieve required services/repositories.
    public RESTDiagnosisInfomationController(DiagnosisServiceInterface diagnosisService,
                                             DiagnosisInformationRepositoryJDBC diagnosisInformationRepositoryJDBC,
                                             NotificationServiceInterface notificationService){

        this.diagnosisService=diagnosisService;
        this.diagnosisInformationRepositoryJDBC = diagnosisInformationRepositoryJDBC;
        this.notificationService = notificationService;
    }

    @GetMapping("/returnedDiagnosisInfo/{index}")
    public @ResponseBody List<DiagnosisInformation> getDiagnosisInformation(@PathVariable Long index) {
        return diagnosisInformationRepositoryJDBC.getDiagnosisInformationByDiagnosisId(index);
    }

    @RequestMapping(value = "/createDiagnosisInformation", method = POST, produces = "application/json")
    public @ResponseBody ResponseEntity<?> saveDiagnosisInformation(@RequestBody Map<String, String> formData) {
        System.out.println(formData);
        System.out.println("Showing what is being saved in form");
        System.out.println(formData.get("diagnosisId"));
        System.out.println(formData.get("key"));
        System.out.println(formData.get("value"));

        Long diagnosisID = Long.parseLong(formData.get("diagnosisId"));

        diagnosisInformationRepositoryJDBC.saveDiagnosisInformation(
                new DiagnosisInformationAdded(null,
                        diagnosisID,
                        formData.get("key"),
                        formData.get("value")
                )
        );

        Notifications n = notificationService.findByDiagnosisLink(diagnosisService.findById(diagnosisID).get());
        if(n != null){
            System.out.println("notification: " + n);
            n.setRead(true);
            n.setDone(true);
            notificationService.saveNotification(n);
        }
        AjaxResponseBody responseBody = new AjaxResponseBody();
        return ResponseEntity.ok().body(responseBody);
    }
}

