package com.example.capstone1.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Merchant {
    @NotNull(message = "id can't be empty")
    private int id;

    @NotEmpty(message = "The name can't be empty.")
    @Size(min = 4, message = "The name should be more then 3 char.")
    private String name;
}
