package nsa.group4.medical.service;

import nsa.group4.medical.domains.CaseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CaseService implements CaseServiceInterface {
    @Autowired
    private CaseRepositoryInterface caseRepository;

    public CaseService(CaseRepositoryInterface caseRepository){
        this.caseRepository = caseRepository;
    }

    @Override
    public Optional<CaseModel> findByCaseName(String caseName) {
        return caseRepository.findByName(caseName);
    }

    @Override
    public Optional<CaseModel> findByCaseId(Long Id) {
        return caseRepository.findById(Id);
    }

    @Override
    public void createCase(CaseModel caseModel) {
        caseRepository.save(caseModel);
    }
}
