package com.blogs_apps_api.contoller;

import com.blogs_apps_api.payloads.ApiResponse;
import com.blogs_apps_api.payloads.CategoryDto;
import com.blogs_apps_api.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // create
    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto)
    {
        CategoryDto createdCategory = this.categoryService.createCategory(categoryDto);

        return  new ResponseEntity<>(createdCategory , HttpStatus.CREATED);
    }

    // update
    @PostMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto , Integer categoryId)
    {
        CategoryDto updatedCategory = this.categoryService.updateCategory(categoryDto,categoryId);

        return  new ResponseEntity<>(updatedCategory , HttpStatus.OK);
    }

    //delete
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> deleteUser (@PathVariable("userId") Integer categoryId )
    {
        this.categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(new ApiResponse("category deleted successfully" , true), HttpStatus.OK);
    }

    // get
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById (@PathVariable Integer categoryId )
    {
        return ResponseEntity.ok( this.categoryService.getCategory(categoryId));
    }

    // getAll
    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getAllCategories ()
    {
      List<CategoryDto> categoryDtoList =  this.categoryService.getAllCategory();
        return ResponseEntity.ok(categoryDtoList);
    }
}
