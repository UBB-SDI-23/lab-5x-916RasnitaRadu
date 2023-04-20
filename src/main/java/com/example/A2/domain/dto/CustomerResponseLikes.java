package com.example.A2.domain.dto;

import lombok.Data;

@Data
public class CustomerResponseLikes {
    private Long id;

    private String firstName;

    private String lastName;

    private Double likes;
}
