package com.example.A2.domain.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
public class CustomerWithReviewDTO {
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String address;

    private Integer age;

    private List<ReviewRequest> reviewList;
}
