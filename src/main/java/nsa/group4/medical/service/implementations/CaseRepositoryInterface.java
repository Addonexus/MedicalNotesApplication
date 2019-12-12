package nsa.group4.medical.service.implementations;

import nsa.group4.medical.domains.CaseModel;
import nsa.group4.medical.domains.Categories;
import nsa.group4.medical.domains.User;
import org.springframework.data.repository.RepositoryDefinition;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CaseRepositoryInterface {

    List<CaseModel> findByCreationDateBetween(LocalDateTime creationDate, LocalDateTime creationDate2);
    List<CaseModel> findByUserAndCreationDateBetween(User user, LocalDateTime creationDate, LocalDateTime creationDate2);

    Optional<CaseModel> findByNameAndUser(String caseName, User user);
    Optional<CaseModel> findByName(String CaseName);

    Optional<CaseModel> findById(Long id);

    CaseModel save(CaseModel caseModel);

    void deleteById(Long id);

    List<CaseModel> findByUser(User user);
    List<CaseModel> findAll();


    List<CaseModel> findAllByOrderByCreationDate();

    List<CaseModel> findAllByOrderByCreationDateAsc();
List<CaseModel> findByUserOrderByCreationDateDesc(User user);
    List<CaseModel> findAllByUserOrderByCreationDateDesc(User user);

    List<CaseModel> findAllByOrderByCreationDateDesc();
}
