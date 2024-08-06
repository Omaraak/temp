package com.example.capstone1.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
public class User {
    @NotNull(message = "id can't be empty")
    private int id;
    @NotEmpty(message = "userName can't be empty")
    @Size(min = 6, message = "User name has to be more then 5 char")
    private String userName;
    @NotEmpty(message = "password can't be empty")
//    @Pattern(regexp = "^([a-zA-Z0-9]+[_-])*[a-zA-Z0-9]+\\.[a-zA-Z0-9]+")
    private String password;
    @Email(message = "Must be email format.")
    @NotEmpty(message = "email can;t be empty")
    private String email;
    @NotEmpty(message = "role can't be empty")
    @Pattern(regexp = "(Admin|Customer)")
    private String role;
    @NotNull(message = "balance can't be empty")
    @Positive(message = "balance should be a positive number.")
    private double balance;
    //it contains all the product user bout
    private final ArrayList<Product> purchasedProducts = new ArrayList<>();

    public User(int id, String userName, String password, String email, String role, double balance) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.role = role;
        this.balance = balance;
    }
}
