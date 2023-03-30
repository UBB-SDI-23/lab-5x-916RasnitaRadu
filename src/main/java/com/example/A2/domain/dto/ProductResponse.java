package com.example.A2.domain.dto;

import lombok.Data;

@Data
public class ProductResponse {
    private Long id;

    private String name;

    private double price;

    private String description;

    private String color;

    private String category;
}
