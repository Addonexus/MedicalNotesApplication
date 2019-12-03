package nsa.group4.medical.service;

import nsa.group4.medical.domains.CaseModel;

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
