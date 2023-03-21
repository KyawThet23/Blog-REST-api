package com.example.blogrestapi.service;

import com.example.blogrestapi.entity.Category;
import com.example.blogrestapi.payload.CategoryDto;

import java.util.List;

public interface CategoryService {
    Category addCategory(CategoryDto request);
    List<CategoryDto> getAllCategory();
    CategoryDto getCategoryById(Long id);
    String deleteById(Long id);
    CategoryDto update(Long id,CategoryDto categoryDto);
}
