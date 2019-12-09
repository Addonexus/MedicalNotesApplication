package nsa.group4.medical.data;

import nsa.group4.medical.domains.Categories;
import nsa.group4.medical.service.implementations.CategoryRepositoryInterface;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoriesRepositoryJPA extends CategoryRepositoryInterface, JpaRepository<Categories, Long> {

}
