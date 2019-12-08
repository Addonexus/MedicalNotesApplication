package nsa.group4.medical.data;

import nsa.group4.medical.domains.Diagnosis;
import nsa.group4.medical.domains.Notifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepoJPA extends JpaRepository<Notifications, Long> {

    Notifications findByDiagnosisLink(Diagnosis diagnosis);
}
