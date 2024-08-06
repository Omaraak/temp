package com.example.capstone1.Controller;

import com.example.capstone1.Api.ApiResponse;
import com.example.capstone1.Model.Category;
import com.example.capstone1.Service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController{
    private final CategoryService categoryService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addCategory(@Valid @RequestBody Category category, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        categoryService.AddCategory(category);
        return ResponseEntity.status(201).body(new ApiResponse("Category added successfully"));
    }

    @GetMapping("/getAll")
    public ResponseEntity getAllCategory(){
        if (!categoryService.getCategories().isEmpty()){
            return ResponseEntity.status(200).body(categoryService.getCategories());
        }
        return ResponseEntity.status(404).body(new ApiResponse("No category found"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable int id, @Valid@RequestBody Category category, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        if (categoryService.updateCategory(category, id))
            return ResponseEntity.status(200).body(new ApiResponse("Category updated successfully"));
        return ResponseEntity.status(404).body(new ApiResponse("No category found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable int id){
        if (categoryService.deleteCategory(id))
            return ResponseEntity.status(200).body(new ApiResponse("Category deleted successfully"));
        return ResponseEntity.status(404).body(new ApiResponse("No category found"));
    }
}
