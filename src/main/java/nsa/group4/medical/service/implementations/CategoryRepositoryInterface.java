package nsa.group4.medical.service.implementations;

import nsa.group4.medical.domains.Categories;

import java.util.List;
import java.util.Optional;

public interface CategoryRepositoryInterface {
    Optional<Categories> findByName(String name);
    Optional<Categories> findById(Long index);


    List<Categories> findByNameContaining(String contains);
    boolean existsByName(String name);

    void deleteById(Long id);
    List<Categories> findAll();
    Categories save(Categories categories);
}
