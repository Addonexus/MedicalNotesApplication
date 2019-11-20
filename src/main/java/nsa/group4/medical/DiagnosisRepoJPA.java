package nsa.group4.medical;

import nsa.group4.medical.domains.CaseModel;
import nsa.group4.medical.domains.Diagnosis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiagnosisRepoJPA extends JpaRepository<Diagnosis, Long> {

}
