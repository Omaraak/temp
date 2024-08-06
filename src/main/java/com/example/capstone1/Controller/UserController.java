package com.example.capstone1.Controller;

import com.example.capstone1.Api.ApiResponse;
import com.example.capstone1.Model.User;
import com.example.capstone1.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addUser(@Valid @RequestBody User user, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        userService.addUser(user);
        return ResponseEntity.status(201).body(new ApiResponse("User added successfully"));
    }

    @GetMapping("/getAll")
    public ResponseEntity getAllUsers(){
        if (!userService.getUsers().isEmpty()){
            return ResponseEntity.status(200).body(userService.getUsers());
        }
        return ResponseEntity.status(404).body(new ApiResponse("No user found"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateUser(@PathVariable int id, @Valid@RequestBody User user, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        if (userService.updateUser(user, id))
            return ResponseEntity.status(200).body(new ApiResponse("User updated successfully"));
        return ResponseEntity.status(404).body(new ApiResponse("No user found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable int id){
        if (userService.deleteUser(id))
            return ResponseEntity.status(200).body(new ApiResponse("User deleted successfully"));
        return ResponseEntity.status(404).body(new ApiResponse("No user found"));
    }

    @PutMapping("/buy/{userId}/{productId}/{merchantId}")
    public ResponseEntity buy(@PathVariable int userId, @PathVariable int productId, @PathVariable int merchantId){
        String message = userService.buy(userId, productId, merchantId);
        if (message.equalsIgnoreCase("Successfully bought product")) {
            return ResponseEntity.status(200).body(new ApiResponse(message));
        }
        return ResponseEntity.status(404).body(new ApiResponse(message));
    }

    @PutMapping("/addToCart/{userId}/{productId}/{merchantId}/{quantity}")
    public ResponseEntity<ApiResponse> addToCart(@PathVariable int userId, @PathVariable int productId, @PathVariable int merchantId, @PathVariable int quantity){
        boolean status = userService.addToCart(userId, productId, merchantId, quantity);
        if (status)
            return ResponseEntity.status(200).body(new ApiResponse("Successfully added product to cart"));
        return ResponseEntity.status(404).body(new ApiResponse("Failed to add product to cart"));
    }

    @PutMapping("/checkout/{userId}")
    public ResponseEntity<ApiResponse> checkout(@PathVariable int userId){
        String message = userService.checkout(userId);
        if (message.equalsIgnoreCase("Successfully bought all items in cart"))
            return ResponseEntity.status(200).body(new ApiResponse(message));
        return ResponseEntity.status(404).body(new ApiResponse(message));
    }
}
