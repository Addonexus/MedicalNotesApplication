package nsa.group4.medical.data;

import nsa.group4.medical.domains.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoriesRepositoryJPA extends JpaRepository<Categories, Long> {
    Optional<Categories> findByName(String name);
    Optional<Categories> findById(Long index);


    List<Categories> findByNameContaining(String contains);
    boolean existsByName(String name);

    void deleteById(Long id);
}
