/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.mii.mcc63lmsserverside.service;

import co.id.mii.mcc63lmsserverside.repository.CategoryRepository;
import co.id.mii.mcc63lmsserverside.model.Category;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author Agung
 */
@Service
public class CategoryService {
    
    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }
    
    public Category getById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() 
                -> new ResponseStatusException(HttpStatus.CREATED, "Category not Found."));
    }
    
    public Category create(Category category) {
        return categoryRepository.save(category);
    }
    
    public Category update(Long id, Category category) {
        getById(id);
        category.setId(id);
        return categoryRepository.save(category);
    }
    
    public Category delete(Long id) {
        Category category = getById(id);
        categoryRepository.delete(category);
        return category;
    }
}