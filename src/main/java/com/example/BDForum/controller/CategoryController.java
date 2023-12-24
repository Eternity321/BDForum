package com.example.BDForum.controller;

import com.example.BDForum.model.Category;
import com.example.BDForum.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String getAllCategories(Model model) {
        try {
            List<Category> categories = categoryService.getAllCategories();
            model.addAttribute("категории", categories);
            return "category_list";
        } catch (Exception e) {
            model.addAttribute("сообщение об ошибке", "Ошибка при получении категорий.");
            return "error";
        }
    }


    @PostMapping
    public ResponseEntity<String> createCategory(@RequestBody Category category) {
        try{
        categoryService.createCategory(category);
        return ResponseEntity.ok("Категория успешно создана.");
        } catch (Exception e) {
            throw new RuntimeException("Ошибка добавления категории.", e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        try{
        categoryService.updateCategory(id, category);
        return ResponseEntity.ok("Категория успешно обновлена.");
    } catch (Exception e) {
        throw new RuntimeException("Ошибка обновления кастегории.", e);
    }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        try{
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Категория успешно удалена.");
    } catch (Exception e) {
        throw new RuntimeException("Ошибка удаления категории.", e);
        }
    }
}
