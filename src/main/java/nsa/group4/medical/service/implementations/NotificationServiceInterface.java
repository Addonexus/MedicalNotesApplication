package nsa.group4.medical.service.implementations;

import nsa.group4.medical.domains.Diagnosis;
import nsa.group4.medical.domains.Notifications;

import java.util.List;

public interface NotificationServiceInterface {
    void saveNotification(Notifications notification);
    Notifications findByDiagnosisLink(Diagnosis diagnosis);
    List<Notifications> getAllNotifications();

}
