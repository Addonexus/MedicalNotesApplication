package nsa.group4.medical.data;

import nsa.group4.medical.domains.CaseModel;
import nsa.group4.medical.service.implementations.CaseRepositoryInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CaseRepositoryJPA extends CaseRepositoryInterface, JpaRepository<CaseModel, Long> {

    List<CaseModel> findByCreationDateBetween(LocalDateTime creationDate, LocalDateTime creationDate2);

    Optional<CaseModel>findByName(String name);
}
