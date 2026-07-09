package com.esma.taskmanager.service;

import com.esma.taskmanager.entity.Category;
import com.esma.taskmanager.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category findById(Long id){
        return categoryRepository.findById(id).orElse(null);
    }

    public Category create(Category category){
        return categoryRepository.save(category);
    }

    public List<Category> findAll(){
        return categoryRepository.findAll();
    }

}
