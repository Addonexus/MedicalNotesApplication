package nsa.group4.medical.data;

import nsa.group4.medical.domains.Diagnosis;
import nsa.group4.medical.domains.Notifications;
import nsa.group4.medical.service.implementations.NotificationRepositoryInterface;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public interface NotificationRepoJPA extends NotificationRepositoryInterface, JpaRepository<Notifications, Long> {

}
