package nsa.group4.medical.service;

import nsa.group4.medical.domains.CaseModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

public interface CaseServiceInterface {
    Optional<CaseModel> findByCaseName(String caseName);
    Optional<CaseModel> findByCaseId(Long Id);
    void createCase(CaseModel caseModel);
    List<CaseModel> findAll();

    List<CaseModel> findCasesByDiagnosisId(Long index);

}
