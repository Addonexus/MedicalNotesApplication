package nsa.group4.medical.service;

import nsa.group4.medical.domains.CaseModel;
import nsa.group4.medical.domains.Categories;
import nsa.group4.medical.domains.Diagnosis;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

//@Component
public interface DiagnosisServiceInterface {
    Optional<Diagnosis> getCaseByDiagnosisName(String name);
    List<Diagnosis> findByCaseNameIn(Collection<String> names);

//    List<Diagnosis> getByCategoryId(Long index);

    Optional<Diagnosis> getByDiagnosisId(Long index);
    List<Diagnosis> findByCategories(Categories categories);
    Diagnosis createDiagnosis(Diagnosis diagnosis);
}
