package nsa.group4.medical.service;

import nsa.group4.medical.domains.CaseModel;
import org.springframework.stereotype.Component;

import java.util.Optional;

public interface CaseRepositoryInterface {

    Optional<CaseModel> findByName(String CaseName);

    Optional<CaseModel> findById(Long id);

    CaseModel save(CaseModel caseModel);

    void deleteById(Long id);



}
