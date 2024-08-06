package com.example.capstone1.Controller;

import com.example.capstone1.Api.ApiResponse;
import com.example.capstone1.Model.Merchant;
import com.example.capstone1.Service.MerchantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/merchant")
@RequiredArgsConstructor
public class MerchantController {
    private final MerchantService merchantService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addMerchant(@Valid @RequestBody Merchant merchant, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        merchantService.addMerchant(merchant);
        return ResponseEntity.status(201).body(new ApiResponse("Merchant added successfully"));
    }

    @GetMapping("/getAll")
    public ResponseEntity getAllMerchant(){
        if (!merchantService.getMerchants().isEmpty()){
            return ResponseEntity.status(200).body(merchantService.getMerchants());
        }
        return ResponseEntity.status(404).body(new ApiResponse("No merchant found"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateMerchant(@PathVariable int id, @Valid@RequestBody Merchant merchant, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        if (merchantService.updateMerchant(merchant, id))
            return ResponseEntity.status(200).body(new ApiResponse("Merchant updated successfully"));
        return ResponseEntity.status(404).body(new ApiResponse("No merchant found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteMerchant(@PathVariable int id){
        if (merchantService.deleteMerchant(id))
            return ResponseEntity.status(200).body(new ApiResponse("Merchant deleted successfully"));
        return ResponseEntity.status(404).body(new ApiResponse("No merchant found"));
    }
}
