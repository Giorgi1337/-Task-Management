package api.taskmanagement.service;

import api.taskmanagement.exception.CategoryNotFoundException;
import api.taskmanagement.model.Category;
import api.taskmanagement.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category getCategoryById(int id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + id));
    }

    public Category updateCategory(int id, Category category) {
       Category existingCategory = categoryRepository.findById(id)
               .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + id));

       if (category.getName() != null) {
           existingCategory.setName(category.getName());
       }
       if (category.getDescription() != null) {
           existingCategory.setDescription(category.getDescription());
       }

       return categoryRepository.save(existingCategory);
    }

    public void deleteCategory(int id) {
        if (!categoryRepository.existsById(id))
            throw new CategoryNotFoundException("Category not found with id: " + id);

        categoryRepository.deleteById(id);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
