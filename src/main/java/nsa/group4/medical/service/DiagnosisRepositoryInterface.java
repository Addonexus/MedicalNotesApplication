package nsa.group4.medical.service;

import nsa.group4.medical.domains.Diagnosis;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface DiagnosisRepositoryInterface {
    Optional<Diagnosis> findByName(String name);
    List<Diagnosis> findByNameIn(Collection<String> names);

    Optional<Diagnosis> findById(Long index);

    List<Diagnosis> findByCategoryId(Long index);



}
