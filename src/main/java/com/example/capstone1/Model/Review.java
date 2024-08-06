package com.example.capstone1.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
@AllArgsConstructor
public class Review {
    @NotNull(message = "id can't be empty")
    private int id;
    @NotEmpty(message = "title can't be empty")
    private String title;
    @NotEmpty(message = "description can't be empty")
    private String description;
    @NotNull(message = "rating can't be empty")
    @Range(min = 1, max = 10, message = "rating must be from 1 to 10")
    private int rating;
    @NotNull(message = "user id can't be empty")
    private int userID;
    @NotNull(message = "product id can't be empty")
    private int productID;
}
