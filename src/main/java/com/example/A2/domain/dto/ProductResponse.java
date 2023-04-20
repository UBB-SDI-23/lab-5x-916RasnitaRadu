package com.example.A2.domain.dto;

import com.example.A2.domain.Product;
import lombok.Data;


@Data
public class ProductResponse {
    private Long id;

    private String name;

    private double price;

    private String description;

    private String color;

    private String category;

    public ProductResponse() {}

    public ProductResponse(Long id, String  name, double price, String description, String color, String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
        this.color = color;
    }

    public static ProductResponse fromProduct(Product product)
    {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getDescription(),
                product.getColor(),
                product.getCategory()
        );
    }
}
