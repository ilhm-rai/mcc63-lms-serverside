/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.mii.mcc63lmsserverside.category;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author Agung
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    
    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    
    @GetMapping
    public List<Category> getAll() {
        return categoryService.getAll();
    }
    
    @GetMapping("/{id}")
    public Category getById(@PathVariable Long id) {
        return categoryService.getById(id);
    }
    
    @PostMapping
    public Category create(@RequestBody Category category) {
        return categoryService.create(category);
    }
    
    @PutMapping("/{id}")
    public Category update(@PathVariable Long id, @RequestBody Category category) {
        return categoryService.update(id, category);
    }
    
    @DeleteMapping("/{id}")
    public Category delete(@PathVariable Long id) {
        return categoryService.delete(id);
    }
}