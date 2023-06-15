package com.blogs_apps_api.services.Impl;

import com.blogs_apps_api.entities.Categories;
import com.blogs_apps_api.globalExceptions.ResourceNotFoundException;
import com.blogs_apps_api.payloads.CategoryDto;
import com.blogs_apps_api.repositories.CategoryRepo;
import com.blogs_apps_api.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Categories cat = this.modelMapper.map(categoryDto, Categories.class);
        Categories addedCategories = this.categoryRepo.save(cat);
        return this.modelMapper.map(addedCategories , CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {

        Categories cat = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category" , "Category Id" , categoryId));

        cat.setCategoryName(categoryDto.getCategoryName());
        cat.setCategoryDesc(categoryDto.getCategoryDesc());

        Categories updatedCategory = this.categoryRepo.save(cat);

        return this.modelMapper.map(updatedCategory , CategoryDto.class);
    }

    @Override
    public void deleteCategory(Integer categoryId) {

        Categories cat = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category" , "Category id" , categoryId));
        this.categoryRepo.delete(cat);

    }

    @Override
    public CategoryDto getCategory(Integer categoryId) {
        Categories categories = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category" , "Category id" , categoryId));
        return this.modelMapper.map(categories,CategoryDto.class);

    }

    @Override
    public List<CategoryDto> getAllCategory() {
        List<CategoryDto> listOfCategories = this.categoryRepo.findAll().stream().map( c  -> this.modelMapper.map(c , CategoryDto.class)).collect(Collectors.toList());
        return listOfCategories;

    }
}
