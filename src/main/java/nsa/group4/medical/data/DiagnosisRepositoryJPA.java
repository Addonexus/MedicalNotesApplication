package nsa.group4.medical.data;

import nsa.group4.medical.domains.CaseModel;
import nsa.group4.medical.domains.Categories;
import nsa.group4.medical.domains.Diagnosis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface DiagnosisRepositoryJPA extends JpaRepository<Diagnosis, Long> {

    Optional<Diagnosis>findByName(String name);
    List<Diagnosis> findByNameIn(Collection<String> names);

    List<Diagnosis> findByCategories(Categories categories);
}
