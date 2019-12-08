package nsa.group4.medical.service;

import lombok.extern.slf4j.Slf4j;
import nsa.group4.medical.domains.Diagnosis;
import nsa.group4.medical.domains.Notifications;
import nsa.group4.medical.service.implementations.NotificationRepositoryInterface;
import nsa.group4.medical.service.implementations.NotificationServiceInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class NotificationService implements NotificationServiceInterface {
    private NotificationRepositoryInterface notificationRepository;

    public NotificationService(NotificationRepositoryInterface notificationRepository){
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void saveNotification(Notifications notification) {
        notificationRepository.save(notification);
    }

    @Override
    public Notifications findByDiagnosisLink(Diagnosis diagnosis) {
        return notificationRepository.findByDiagnosisLink(diagnosis);
    }

    @Override
    public List<Notifications> getAllNotifications() {
        return notificationRepository.findAll();
    }
}
