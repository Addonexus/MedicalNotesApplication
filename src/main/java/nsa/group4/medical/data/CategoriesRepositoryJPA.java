package nsa.group4.medical.data;

import nsa.group4.medical.domains.Categories;
import nsa.group4.medical.service.implementations.CategoryRepositoryInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.List;
import java.util.Optional;

@RepositoryDefinition(domainClass = Categories.class, idClass = String.class)
public interface CategoriesRepositoryJPA extends CategoryRepositoryInterface {

}
