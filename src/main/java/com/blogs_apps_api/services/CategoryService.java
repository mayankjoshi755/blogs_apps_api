package com.blogs_apps_api.services;

import com.blogs_apps_api.payloads.CategoryDto;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface CategoryService {

    // create
    CategoryDto createCategory(CategoryDto categoryDto);

    //update
    CategoryDto updateCategory(CategoryDto categoryDto , Integer categoryId);

    // delete
    void deleteCategory(Integer categoryId);

    //Get
    CategoryDto getCategory(Integer categoryId);

    //Get All
    List<CategoryDto> getAllCategory();
}
