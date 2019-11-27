package nsa.group4.medical.service;

import nsa.group4.medical.domains.CaseModel;
import java.util.List;
import java.util.Optional;

public interface CaseRepositoryInterface {

    Optional<CaseModel> findByName(String CaseName);

    Optional<CaseModel> findById(Long id);

    CaseModel save(CaseModel caseModel);

    void deleteById(Long id);

    List<CaseModel> findAll();


    List<CaseModel> findAllByOrderByCreationDate();
}
