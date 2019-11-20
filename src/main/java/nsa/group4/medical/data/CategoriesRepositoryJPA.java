package nsa.group4.medical.data;

import nsa.group4.medical.domains.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriesRepositoryJPA extends JpaRepository<Categories, Long> {
    Optional<Categories> findByName(String name);

}
