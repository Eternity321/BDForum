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
        try {
            return categoryRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при получении категорий.", e);
        }
    }

    public Optional<Category> getCategoryById(Long categoryId) {
        try {
            return categoryRepository.findById(categoryId);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка получения категории по идентификатору: " + categoryId, e);
        }
    }

    public void createCategory(Category category) {
        try {
        categoryRepository.save(category);
    } catch (Exception e) {
        throw new RuntimeException("Ошибка добавления котегории: " + category, e);
        }
    }

    public void updateCategory(Long categoryId, Category updatedCategory) {
        try {
            categoryRepository.findById(categoryId).ifPresent(category -> {
                category.setTitle(updatedCategory.getTitle());
                categoryRepository.save(category);
            });
        } catch (Exception e) {
        throw new RuntimeException("Ошибка обновления категории: " + updatedCategory, e);
        }
    }

    public void deleteCategory(Long categoryId) {
        try {
        categoryRepository.deleteById(categoryId);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка удаления категории: " + categoryId, e);
        }
    }
}
