package nsa.group4.medical.data;

import nsa.group4.medical.domains.CaseModel;
import nsa.group4.medical.domains.Diagnosis;
import nsa.group4.medical.domains.Notifications;
import nsa.group4.medical.service.CaseRepositoryInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepoJPA extends JpaRepository<Notifications, Long> {

    Notifications findByDiagnosisLink(Diagnosis diagnosis);
}
