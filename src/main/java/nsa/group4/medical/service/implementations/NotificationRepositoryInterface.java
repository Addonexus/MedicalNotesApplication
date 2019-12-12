package nsa.group4.medical.service.implementations;

import nsa.group4.medical.domains.Diagnosis;
import nsa.group4.medical.domains.Notifications;
import nsa.group4.medical.domains.User;
import nsa.group4.medical.service.events.NotificationAdded;

import java.util.List;

public interface NotificationRepositoryInterface {
    Notifications findByDiagnosisLink(Diagnosis diagnosis);
    Notifications save(Notifications notifications);
    List<Notifications> findAll();
    List<Notifications> findByUser(User user);
}
