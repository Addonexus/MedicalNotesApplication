package nsa.group4.medical.service.implementations;

import nsa.group4.medical.domains.Categories;
import nsa.group4.medical.domains.User;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface CategoryRepositoryInterface {
    Optional<Categories> findByNameAndUser(String name, User user);
    Optional<Categories> findByName(String name);
    Optional<Categories> findById(Long index);


    List<Categories> findByNameContaining(String contains);
    boolean existsByNameAndUser(String name, User user);
    @Transactional
    void deleteById(Long id);
    List<Categories> findAll();
    Categories save(Categories categories);
    List<Categories> findByUser(User user);
}
