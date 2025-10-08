package org.example.h2dbproject.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @Email(message = "Email should be valid" )
    private String email;

    @Min(value = 100, message = "Price should not be less than 100" )
    private double price;

    @NotBlank(message = "Description cannot be blank" )
    private String description;

    @Max(value = 10, message = "Quantity must be at most 10" )
    private int quantity;
}
