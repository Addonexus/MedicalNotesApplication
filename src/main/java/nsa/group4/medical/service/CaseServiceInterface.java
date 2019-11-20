package nsa.group4.medical.service;

import nsa.group4.medical.domains.CaseModel;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface CaseServiceInterface {
    Optional<CaseModel> findByCaseName(String caseName);
    Optional<CaseModel> findByCaseId(Long Id);
    void createCase(CaseModel caseModel);
}
