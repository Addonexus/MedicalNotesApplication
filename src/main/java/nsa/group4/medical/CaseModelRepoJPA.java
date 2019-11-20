package nsa.group4.medical;

import nsa.group4.medical.domains.CaseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaseModelRepoJPA extends JpaRepository<CaseModel, Long> {

}
