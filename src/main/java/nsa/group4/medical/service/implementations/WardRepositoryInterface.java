package nsa.group4.medical.service.implementations;

import nsa.group4.medical.domains.Categories;
import nsa.group4.medical.domains.Diagnosis;
import nsa.group4.medical.domains.User;
import nsa.group4.medical.domains.Ward;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface WardRepositoryInterface {
    Optional<Ward> findByName(String name);

    Optional<Ward> findByNameAndUser(String name, User user);

    List<Ward> findByNameIn(Collection<String> names);
    List<Ward> findByUserAndNameIn(User user, Collection<String> names);

    Ward save(Ward ward);

    List<Ward> findByUser(User user);

    List<Ward> findAll();

    Optional<Ward> findById(Long index);



}
