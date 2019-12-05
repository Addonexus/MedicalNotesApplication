package nsa.group4.medical.service;

import nsa.group4.medical.domains.CaseModel;
import nsa.group4.medical.web.CaseForm;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CaseServiceInterface {
    Optional<CaseModel> findByCaseName(String caseName);
    Optional<CaseModel> findByCaseId(Long Id);
    void createCase(CaseForm caseForm);
    List<CaseModel> findAll();

    List<CaseModel> findCasesByDiagnosisId(Long index);

    List<CaseModel> findAllByOrderByCreationDate();

    List<CaseModel> findAllByOrderByCreationDateAsc();

    void updateCase(CaseForm formData);

    void deleteCaseById(Long id);
    void checkEmptyDiagnosis();

    List<CaseModel> findByCreationDateBetween(LocalDateTime creationDate, LocalDateTime creationDate2);

}
