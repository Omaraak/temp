package com.example.capstone1.Controller;

import com.example.capstone1.Api.ApiResponse;
import com.example.capstone1.Model.MerchantStock;
import com.example.capstone1.Service.MerchantStockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/merchantStock")
@RequiredArgsConstructor
public class MerchantStockController {
    private final MerchantStockService merchantStockService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addMerchantStock(@Valid @RequestBody MerchantStock merchantStock, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        String message = merchantStockService.addMerchantStock(merchantStock);
        if (message.equalsIgnoreCase("Merchant stock added successfully"))
            return ResponseEntity.status(201).body(new ApiResponse(message));
        return ResponseEntity.status(400).body(new ApiResponse(message));
    }

    @GetMapping("/getAll")
    public ResponseEntity getAllMerchantStacks(){
        if (!merchantStockService.getMerchantStocks().isEmpty()){
            return ResponseEntity.status(200).body(merchantStockService.getMerchantStocks());
        }
        return ResponseEntity.status(404).body(new ApiResponse("No merchantStock found"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateMerchantStock(@PathVariable int id, @Valid@RequestBody MerchantStock merchantStock, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        if (merchantStockService.updateMerchantStock(merchantStock, id))
            return ResponseEntity.status(200).body(new ApiResponse("MerchantStock updated successfully"));
        return ResponseEntity.status(404).body(new ApiResponse("No merchantStock found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteMerchant(@PathVariable int id){
        if (merchantStockService.deleteMerchantStock(id))
            return ResponseEntity.status(200).body(new ApiResponse("MerchantStock deleted successfully"));
        return ResponseEntity.status(404).body(new ApiResponse("No merchantStock found"));
    }
}
