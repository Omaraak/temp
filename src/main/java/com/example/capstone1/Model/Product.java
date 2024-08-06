package com.example.capstone1.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
@AllArgsConstructor
public class Product {
    @NotNull(message = "id can't be empty.")
    private int id;

    @NotEmpty(message = "Name can't be empty.")
    @Size(min = 4, message = "name should be between 4 and 16 char.")
    private String name;

    @NotNull(message = "price can't be empty")
    @Positive(message = "price have to be a positive value")
    private double price;

    @NotNull(message = "categoryID can't be empty")
    private int categoryID;
}
