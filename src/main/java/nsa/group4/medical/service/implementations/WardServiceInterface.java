package nsa.group4.medical.service.implementations;

import nsa.group4.medical.domains.Ward;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface WardServiceInterface {

    Optional<Ward> getCaseByWardName(String name);
    List<Ward> findByWardNameIn(Collection<String> names);

    Optional<Ward> findById(Long wardId);

    List<Ward> getAllWard();

    Optional<Ward> findByName(String name);

    List<Ward> findAll();

}
