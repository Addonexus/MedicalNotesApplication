package nsa.group4.medical.controllers.api;

import lombok.extern.slf4j.Slf4j;

import nsa.group4.medical.data.FreehandNotesRepoJDBC;
import nsa.group4.medical.domains.FreehandNotes;
import nsa.group4.medical.domains.Notifications;
import nsa.group4.medical.service.DiagnosisService;
import nsa.group4.medical.service.events.DiagnosisInformationAdded;
import nsa.group4.medical.service.events.FreehandNotesAdded;
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

public class RESTFreehandNotesController {
    private DiagnosisServiceInterface diagnosisServiceInterface;
    private FreehandNotesRepoJDBC freehandNotesRepoJDBC;
    private NotificationServiceInterface notificationService;

    public RESTFreehandNotesController(
            DiagnosisServiceInterface diagnosisServiceInterface,
            FreehandNotesRepoJDBC freehandNotesRepoJDBC, NotificationServiceInterface notificationService) {
        this.diagnosisServiceInterface = diagnosisServiceInterface;
        this.freehandNotesRepoJDBC = freehandNotesRepoJDBC;
        this.notificationService = notificationService;
    }


    @GetMapping("/returnedFreehandNotes/{index}")
    public @ResponseBody List<FreehandNotes> getFreehandNotes
            (@PathVariable Long index){
        return freehandNotesRepoJDBC.getFreehandNotesnByDiagnosisId(index);

    }

    @RequestMapping(value = "/createFreehandNotes", method = POST, produces = "application/json")
    public @ResponseBody ResponseEntity<?> save(@RequestBody Map<String,String> formData) {
        System.out.println(formData);
        System.out.println(formData.get("diagnosisId"));
        System.out.println(formData.get("field"));


        Long diagnosisID = Long.parseLong(formData.get("diagnosisId"));

        freehandNotesRepoJDBC.save(
                new FreehandNotesAdded(null,
                        diagnosisID,
                        formData.get("field"))

        );


        AjaxResponseBody responseBody = new AjaxResponseBody();
        return ResponseEntity.ok().body(responseBody);
    }


}
