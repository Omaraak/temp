package com.example.capstone1.Service;

import com.example.capstone1.Model.Product;
import com.example.capstone1.Model.User;
import com.example.capstone1.Model.Review;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ReviewService {
    ArrayList<Review> reviews = new ArrayList<>();
    private final UserService userService;
    private final ProductService productService;

    public String addReview(Review review) {
        //Check if user exist in the system
        for (User user : userService.users){
            if (user.getId() == review.getUserID()){
                //Check if product exist in the system
                for (Product product : productService.products){
                    if (product.getId() == review.getProductID()){
                        if (!user.getPurchasedProducts().isEmpty()) {
                            //Check in user has bought the product
                            for (Product purchasedProduct : user.getPurchasedProducts()) {
                                if (purchasedProduct.getId() == product.getId()) {
                                    reviews.add(review);
                                    return "Review added successfully";
                                }
                            }
                        }
                        return "You can't add a review, you have to buy it first";
                    }
                }
                return "product not found";
            }
        }
        return "User not found";
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public ArrayList<Review> getReviewsByUserID(int userID) {
        ArrayList<Review> reviewsByUserID = new ArrayList<>();
        for (User user : userService.users){
            if (user.getId() == userID){
                for (Review review : reviews){
                    if (review.getUserID() == userID){
                        reviewsByUserID.add(review);
                    }
                }
            }
        }
        return reviewsByUserID;
    }

    public String updateReview(Review newReview, int reviewId, int userId) {
        for (User user : userService.users){
            if (user.getId() == userId){
                for (int i = 0; i < reviews.size(); i++){
                    if (reviews.get(i).getId() == reviewId){
                        //Check if it is the author
                        if (reviews.get(i).getUserID() == userId){
                            reviews.set(i, newReview);
                            return "Review updated successfully";
                        }
                        return "You are not allowed to update this review";
                    }
                }
                return "Review not found";
            }
        }
        return "User not found";
    }

    public String deleteReview(int reviewId, int userId) {
        for (User user : userService.users){
            if (user.getId() == userId){
                for (Review review : reviews){
                    if (review.getId() == reviewId){
                        //Check if it is the author
                        if (user.getRole().equals("admin")){
                            reviews.remove(review);
                            return "Review removed successfully";
                        }
                        return "You are not allowed to remove this review";
                    }
                }
                return "Review not found";
            }
        }
        return "User not found";
    }
}
