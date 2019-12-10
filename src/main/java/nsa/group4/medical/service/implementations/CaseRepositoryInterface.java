package nsa.group4.medical.service.implementations;

import nsa.group4.medical.domains.CaseModel;
import nsa.group4.medical.domains.Categories;
import org.springframework.data.repository.RepositoryDefinition;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CaseRepositoryInterface {

    List<CaseModel> findByCreationDateBetween(LocalDateTime creationDate, LocalDateTime creationDate2);

    Optional<CaseModel> findByName(String CaseName);

    Optional<CaseModel> findById(Long id);

    CaseModel save(CaseModel caseModel);

    void deleteById(Long id);

    List<CaseModel> findAll();


    List<CaseModel> findAllByOrderByCreationDate();

    List<CaseModel> findAllByOrderByCreationDateAsc();

    List<CaseModel> findAllByOrderByCreationDateDesc();
}
