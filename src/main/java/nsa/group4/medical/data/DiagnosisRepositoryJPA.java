package nsa.group4.medical.data;

import nsa.group4.medical.domains.Diagnosis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiagnosisRepositoryJPA extends JpaRepository<Diagnosis, Long> {

    Optional<Diagnosis>findByName(String name);

    List<Diagnosis> findByCategoryId(Long index);
}
