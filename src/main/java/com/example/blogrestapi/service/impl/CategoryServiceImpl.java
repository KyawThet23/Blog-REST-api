package com.example.blogrestapi.service.impl;

import com.example.blogrestapi.entity.Category;
import com.example.blogrestapi.exception.ResourceNotFoundException;
import com.example.blogrestapi.payload.CategoryDto;
import com.example.blogrestapi.repository.CategoryRepository;
import com.example.blogrestapi.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository repository;
    @Autowired
    private ModelMapper mapper;

    @Override
    public Category addCategory(CategoryDto dto) {
        Category category = dtoToEntity(dto);
        repository.save(category);
        return category;
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        List<Category> categories = repository.findAll();
        return  categories.stream()
                .map( category -> EntityToDto(category))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategoryById(Long id) {
        Category category = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        return EntityToDto(category);
    }

    @Override
    public String deleteById(Long id) {
        repository.deleteById(id);
        return "Deleted successfully.";
    }

    @Override
    public CategoryDto update(Long id,CategoryDto categoryDto) {

        Category categoryToUpdate = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category","Id", id));

        categoryToUpdate.setDescription(categoryDto.getDescription() != null ?
                categoryDto.getDescription() : categoryToUpdate.getDescription());

        categoryToUpdate.setName(categoryToUpdate.getName() != null ?
                categoryDto.getName() : categoryToUpdate.getName());

        repository.save(categoryToUpdate);
        CategoryDto dto = EntityToDto(categoryToUpdate);

        return dto;

    }

    private Category dtoToEntity(CategoryDto categoryDto){
        Category category = mapper.map(categoryDto, Category.class);
        return category;
    }
    private CategoryDto EntityToDto(Category category){
        CategoryDto categoryDto = mapper.map(category, CategoryDto.class);
        return categoryDto;
    }

}
