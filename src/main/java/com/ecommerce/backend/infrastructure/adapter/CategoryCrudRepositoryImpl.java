package com.ecommerce.backend.infrastructure.adapter;

import com.ecommerce.backend.domain.model.Category;
import com.ecommerce.backend.domain.port.ICategoryRepository;
import com.ecommerce.backend.infrastructure.entity.CategoryEntity;
import com.ecommerce.backend.infrastructure.mapper.CategoryMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CategoryCrudRepositoryImpl implements ICategoryRepository {
    private final ICategoryCrudRepository iCategoryCrudRepository;
    private final CategoryMapper categoryMapper;

    public CategoryCrudRepositoryImpl(ICategoryCrudRepository iCategoryCrudRepository, CategoryMapper categoryMapper) {
        this.iCategoryCrudRepository = iCategoryCrudRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public Category save(Category category) {
        return categoryMapper.toCategory(iCategoryCrudRepository.save(categoryMapper.toCategoryEntity(category)));
    }

    @Override
    public Iterable<Category> findAll() {
        return categoryMapper.toCategoryList(iCategoryCrudRepository.findAll());
    }

    @Override
    public Category findById(Integer id) {
        Optional<CategoryEntity> categoryOptional = iCategoryCrudRepository.findById(id);
        if (categoryOptional.isPresent()) {
            return categoryMapper.toCategory(categoryOptional.get());
        } else {
            throw new RuntimeException("Categoria con id: " + id + " no existe");
        }
    }

    @Override
    public void deleteById(Integer id) {
        Optional<CategoryEntity> categoryOptional = iCategoryCrudRepository.findById(id);
        if (categoryOptional.isEmpty()) {
            throw new RuntimeException("Categoria con id: " + id + " no existe");
        }
        iCategoryCrudRepository.deleteById(id);
    }
}
