package nsa.group4.medical.data;

import nsa.group4.medical.domains.CaseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CaseRepositoryJPA extends JpaRepository<CaseModel, Long> {

    Optional<CaseModel>findByName(String name);
}
