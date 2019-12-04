package nsa.group4.medical.controllers;


import lombok.extern.slf4j.Slf4j;
import nsa.group4.medical.data.NotificationRepositoryJDBC;
import nsa.group4.medical.domains.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Slf4j
@Controller
public class NotificationController {

    private NotificationRepositoryJDBC notificationRepositoryJDBC;

    @Autowired
    public NotificationController(NotificationRepositoryJDBC notificationRepositoryJDBC) {this.notificationRepositoryJDBC = notificationRepositoryJDBC;}

    @GetMapping(value="/notifications")
    public String getNotifications(Model model){

        List<Notification> notificationList = notificationRepositoryJDBC.getAllNotifications();

        model.addAttribute("notificationsKey", notificationList);

        return "notifications";
    }
}
