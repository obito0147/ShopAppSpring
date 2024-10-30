package com.example.shoapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTO {
    @JsonProperty("order_id")
    @Min(value = 1, message = "Order's ID must be greater than 0")
    private Long orderId;

    @JsonProperty("product_id")
    @Min(value = 1, message = "Product's ID must be greater than 0")
    private Long productId;

    @Min(value = 1, message = "Price must be greater than 0")
    private Long price;

    @JsonProperty("number_of_products")
    @Min(value = 1, message = "number of products must be greater than 0")
    private int numberOfProducts;

    @JsonProperty("total_money")
    private int totalMoney;

    private String color;
}
