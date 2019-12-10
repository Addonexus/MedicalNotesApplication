package nsa.group4.medical.data;


import nsa.group4.medical.domains.Diagnosis;
import nsa.group4.medical.domains.Ward;
import nsa.group4.medical.service.implementations.WardRepositoryInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface WardRepositoryJPA extends WardRepositoryInterface, JpaRepository<Ward, Long> {
    Optional<Ward> findByName(String name);
    List<Ward> findByNameIn(Collection<String> names);
}
