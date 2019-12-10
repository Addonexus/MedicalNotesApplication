package nsa.group4.medical.service.implementations;

import nsa.group4.medical.domains.Categories;
import nsa.group4.medical.domains.Diagnosis;
import nsa.group4.medical.domains.Ward;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface WardRepositoryInterface {
    Optional<Ward> findByName(String name);

    List<Ward> findByNameIn(Collection<String> names);

    Ward save(Ward ward);

    List<Ward> findAll();

    Optional<Ward> findById(Long index);



}
