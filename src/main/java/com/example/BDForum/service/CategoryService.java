package com.example.BDForum.service;

import com.example.BDForum.model.Category;
import com.example.BDForum.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId);
    }

    public void createCategory(Category category) {
        categoryRepository.save(category);
    }

    public void updateCategory(Long categoryId, Category updatedCategory) {
        Optional<Category> existingCategory = categoryRepository.findById(categoryId);
        if (existingCategory.isPresent()) {
            Category category = existingCategory.get();
            category.setTitle(updatedCategory.getTitle());
            categoryRepository.save(category);
        }
    }

    public void deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}
