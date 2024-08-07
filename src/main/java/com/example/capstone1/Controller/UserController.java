package com.example.capstone1.Controller;

import com.example.capstone1.Api.ApiResponse;
import com.example.capstone1.Model.Review;
import com.example.capstone1.Model.User;
import com.example.capstone1.Service.ReviewService;
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
    private final ReviewService reviewService;

    @PostMapping("/addUser")
    public ResponseEntity<ApiResponse> addUser(@Valid @RequestBody User user, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        userService.addUser(user);
        return ResponseEntity.status(201).body(new ApiResponse("User added successfully"));
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity getAllUsers(){
        if (!userService.getUsers().isEmpty()){
            return ResponseEntity.status(200).body(userService.getUsers());
        }
        return ResponseEntity.status(404).body(new ApiResponse("No user found"));
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<ApiResponse> updateUser(@PathVariable int id, @Valid@RequestBody User user, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        if (userService.updateUser(user, id))
            return ResponseEntity.status(200).body(new ApiResponse("User updated successfully"));
        return ResponseEntity.status(404).body(new ApiResponse("No user found"));
    }

    @DeleteMapping("/deleteUser/{id}")
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

    @PostMapping("/addReview")
    public ResponseEntity<ApiResponse> addReview(@Valid @RequestBody Review review, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        String message = reviewService.addReview(review);
        if (message.equalsIgnoreCase("Review added successfully"))
            return ResponseEntity.status(201).body(new ApiResponse(message));
        return ResponseEntity.status(400).body(new ApiResponse(message));
    }

    @GetMapping("/getAllReviews")
    public ResponseEntity getAllReviews(){
        if (!reviewService.getReviews().isEmpty()){
            return ResponseEntity.status(200).body(reviewService.getReviews());
        }
        return ResponseEntity.status(404).body(new ApiResponse("No review found"));
    }

    @GetMapping("/getReviewsByUserID/{userId}")
    public ResponseEntity getReviewsByUserID(@PathVariable int userId){
        if (reviewService.getReviewsByUserID(userId).isEmpty()){
            return ResponseEntity.status(404).body(new ApiResponse("User dont have reviews"));
        }
        return ResponseEntity.status(200).body(reviewService.getReviewsByUserID(userId));
    }

    @PutMapping("/updateReview/{reviewId}/{userId}")
    public ResponseEntity<ApiResponse> updateReview(@PathVariable int reviewId, @PathVariable int userId, @Valid @RequestBody Review review, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        String message = reviewService.updateReview(review,reviewId,userId);
        if (message.equalsIgnoreCase("Review updated successfully"))
            return ResponseEntity.status(200).body(new ApiResponse(message));
        return ResponseEntity.status(400).body(new ApiResponse(message));
    }

    @DeleteMapping("/deleteReview/{reviewId}/{userId}")
    public ResponseEntity<ApiResponse> deleteReview(@PathVariable int reviewId, @PathVariable int userId){
        String message = reviewService.deleteReview(reviewId,userId);
        if (message.equalsIgnoreCase("Review removed successfully"))
            return ResponseEntity.status(200).body(new ApiResponse(message));
        return ResponseEntity.status(400).body(new ApiResponse(message));
    }

    @GetMapping("/history/{userId}")
    public ResponseEntity getHistory(@PathVariable int userId){
        if (userService.history(userId) == null)
            return ResponseEntity.status(404).body(new ApiResponse("No user found"));
        return ResponseEntity.status(200).body(userService.history(userId));
    }

    @PutMapping("/returnProduct/{userId}/{productId}/{merchantId}")
    public ResponseEntity returnProduct(@PathVariable int userId, @PathVariable int productId, @PathVariable int merchantId){
        String message = userService.returnProduct(userId, productId, merchantId);
        if (message.equalsIgnoreCase("Successfully returned product"))
            return ResponseEntity.status(200).body(new ApiResponse(message));
        return ResponseEntity.status(400).body(new ApiResponse(message));
    }
}
