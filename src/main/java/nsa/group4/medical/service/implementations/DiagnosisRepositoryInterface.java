package nsa.group4.medical.service.implementations;

import nsa.group4.medical.domains.CaseModel;
import nsa.group4.medical.domains.Categories;
import nsa.group4.medical.domains.Diagnosis;
import nsa.group4.medical.domains.User;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface DiagnosisRepositoryInterface {
    Optional<Diagnosis> findByNameAndUser(String name, User user);
    Optional<Diagnosis> findByName(String name);
    List<Diagnosis> findByNameIn(Collection<String> names);

    Optional<Diagnosis> findById(Long index);

//    List<Diagnosis> findByCategoryId(Long index);
    List<Diagnosis> findByCategories(Categories categories);
    Diagnosis save(Diagnosis diagnosis);
    List<Diagnosis> findAll();
    @Transactional
    void deleteById(Long id);
}
