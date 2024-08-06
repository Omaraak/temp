package com.example.capstone1.Controller;

import com.example.capstone1.Api.ApiResponse;
import com.example.capstone1.Model.Product;
import com.example.capstone1.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@Valid @RequestBody Product product, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        if (productService.addProduct(product))
            return ResponseEntity.status(201).body(new ApiResponse("Product added successfully"));
        return ResponseEntity.status(400).body(new ApiResponse("Category not found"));
    }

    @GetMapping("/getAll")
    public ResponseEntity getAllMerchantStacks(){
        if (!productService.getProducts().isEmpty()){
            return ResponseEntity.status(200).body(productService.getProducts());
        }
        return ResponseEntity.status(404).body(new ApiResponse("No product found"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable int id, @Valid@RequestBody Product product, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        if (productService.updateProduct(product, id))
            return ResponseEntity.status(200).body(new ApiResponse("Product updated successfully"));
        return ResponseEntity.status(404).body(new ApiResponse("No product found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteMerchant(@PathVariable int id){
        if (productService.deleteProduct(id))
            return ResponseEntity.status(200).body(new ApiResponse("Product deleted successfully"));
        return ResponseEntity.status(404).body(new ApiResponse("No product found"));
    }
}
