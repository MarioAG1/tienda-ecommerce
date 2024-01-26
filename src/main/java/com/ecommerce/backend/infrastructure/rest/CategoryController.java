package com.ecommerce.backend.infrastructure.rest;

import com.ecommerce.backend.application.CategoryService;
import com.ecommerce.backend.domain.model.Category;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/categories")
@Slf4j
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public Category save(@RequestBody Category category) {
        return categoryService.save(category);
    }

    @GetMapping
    public Iterable<Category> findAll() {
        return categoryService.findAll();
    }

    @GetMapping("/{id}")
    public Category findById(@PathVariable Integer id) {
        return categoryService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id) {
        categoryService.deleteById(id);
    }
}
