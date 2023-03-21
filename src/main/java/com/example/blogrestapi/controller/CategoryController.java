package com.example.blogrestapi.controller;

import com.example.blogrestapi.entity.Category;
import com.example.blogrestapi.payload.CategoryDto;
import com.example.blogrestapi.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/add")
    public String addCategory(@RequestBody CategoryDto request){
        categoryService.addCategory(request);
        return "Category create successfully.";
    }

    @GetMapping("/all")
    public List<CategoryDto> getAllCategory(){
        return categoryService.getAllCategory();
    }

    @GetMapping("/{id}")
    public CategoryDto getCategoryById(@PathVariable Long id){
        return categoryService.getCategoryById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id){
        categoryService.deleteById(id);
    }

    @PutMapping("/{id}")
    public CategoryDto updateCategory(@RequestBody CategoryDto request,
                                      @PathVariable Long id){
       return categoryService.update(id,request);
    }
}