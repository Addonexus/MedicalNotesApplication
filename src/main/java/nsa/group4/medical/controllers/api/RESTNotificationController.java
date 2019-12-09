package nsa.group4.medical.controllers.api;

import lombok.extern.slf4j.Slf4j;
import nsa.group4.medical.domains.*;
import nsa.group4.medical.service.implementations.NotificationServiceInterface;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api")
@Slf4j
public class RESTNotificationController {

    private NotificationServiceInterface notificationService;

    public RESTNotificationController(NotificationServiceInterface notificationService){
        this.notificationService = notificationService;
    }

    @GetMapping("/getAllNotifications")
    public @ResponseBody ResponseEntity<?> getAllNotifications() {
        log.debug("---- GET Mapping: /api/getAllNotifications ----");
        List<Notifications> notificationsList = notificationService.getAllNotifications();
        log.debug("NOTIFICATIONS: " + notificationsList);

        return ResponseEntity.ok().body(notificationsList);
    }
}

