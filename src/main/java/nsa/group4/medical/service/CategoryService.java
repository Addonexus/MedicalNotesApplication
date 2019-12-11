package nsa.group4.medical.service;

import nsa.group4.medical.domains.Categories;
import nsa.group4.medical.domains.User;
import nsa.group4.medical.service.implementations.CategoryRepositoryInterface;
import nsa.group4.medical.service.implementations.CategoryServiceInterface;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements CategoryServiceInterface {
    private CategoryRepositoryInterface categoryRepository;

    public CategoryService(CategoryRepositoryInterface categoryRepository){
        this.categoryRepository=categoryRepository;
    }

    @Override
    public Optional<Categories> findByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public Optional<Categories> findById(Long index) {
        return categoryRepository.findById(index);
    }

    @Override
    public List<Categories> findByNameContaining(String contains) {
        return categoryRepository.findByNameContaining(contains);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public List<Categories> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Categories saveCategory(Categories categories) {
        return categoryRepository.save(categories);
    }

    @Override
    public List<Categories> findByUser(User user) {
       return categoryRepository.findByUser(user);
    }
}
