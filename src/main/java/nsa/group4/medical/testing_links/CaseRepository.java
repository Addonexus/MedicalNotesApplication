package nsa.group4.medical.testing_links;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface CaseRepository extends JpaRepository<ExaminationCase, Long> {
}
