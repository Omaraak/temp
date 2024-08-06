package com.example.capstone1.Model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MerchantStock {
    @NotNull(message = "id can't be empty")
    private int id;
    @NotNull(message = "productID can't be empty")
    private int productID;
    @NotNull(message = "merchantID can't be empty")
    private int merchantID;
    @NotNull(message = "stock can't be empty")
    @Min(value = 10, message = "it has to be 10 at start")
    private int stock;
}
