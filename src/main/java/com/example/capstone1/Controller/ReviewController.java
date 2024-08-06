package com.example.capstone1.Controller;

import com.example.capstone1.Api.ApiResponse;
import com.example.capstone1.Model.Review;
import com.example.capstone1.Model.Review;
import com.example.capstone1.Service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addReview(@Valid @RequestBody Review review, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        String message = reviewService.addReview(review);
        if (message.equalsIgnoreCase("Review added successfully"))
            return ResponseEntity.status(201).body(new ApiResponse(message));
        return ResponseEntity.status(400).body(new ApiResponse(message));
    }

    @GetMapping("/getAll")
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

    @PutMapping("/update/{reviewId}/{userId}")
    public ResponseEntity<ApiResponse> updateReview(@PathVariable int reviewId, @PathVariable int userId, @Valid @RequestBody Review review, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        String message = reviewService.updateReview(review,reviewId,userId);
        if (message.equalsIgnoreCase("Review updated successfully"))
            return ResponseEntity.status(200).body(new ApiResponse(message));
        return ResponseEntity.status(400).body(new ApiResponse(message));
    }

    @DeleteMapping("/delete/{reviewId}/{userId}")
    public ResponseEntity<ApiResponse> deleteReview(@PathVariable int reviewId, @PathVariable int userId){
        String message = reviewService.deleteReview(reviewId,userId);
        if (message.equalsIgnoreCase("Review removed successfully"))
            return ResponseEntity.status(200).body(new ApiResponse(message));
        return ResponseEntity.status(400).body(new ApiResponse(message));
    }
}
