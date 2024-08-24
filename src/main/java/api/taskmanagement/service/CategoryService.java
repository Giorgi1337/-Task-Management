package api.taskmanagement.service;

import api.taskmanagement.dto.CategoryDTO;
import api.taskmanagement.exception.CategoryNotFoundException;
import api.taskmanagement.model.Category;
import api.taskmanagement.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category createCategory(CategoryDTO categoryDTO) {
        Category category = mapDtoToEntity(categoryDTO);
        return categoryRepository.save(category);
    }

    public Category getCategoryById(int id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + id));
    }

    public Category updateCategory(int id, CategoryDTO categoryDTO) {
       Category existingCategory = categoryRepository.findById(id)
               .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + id));

       updateEntityFromDto(existingCategory, categoryDTO);
       return categoryRepository.save(existingCategory);
    }

    public void deleteCategory(int id) {
        if (!categoryRepository.existsById(id))
            throw new CategoryNotFoundException("Category not found with id: " + id);

        categoryRepository.deleteById(id);
    }

    public Page<Category> getAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    private Category mapDtoToEntity(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        return category;
    }

    private void updateEntityFromDto(Category category, CategoryDTO categoryDTO) {
        if (categoryDTO.getName() != null) {
            category.setName(categoryDTO.getName());
        }
        if (categoryDTO.getDescription() != null) {
            category.setDescription(categoryDTO.getDescription());
        }
    }
}
