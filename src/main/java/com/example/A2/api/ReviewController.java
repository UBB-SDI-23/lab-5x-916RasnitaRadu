package com.example.A2.api;

import com.example.A2.domain.dto.*;
import com.example.A2.service.ReviewService;
import com.example.A2.domain.Review;

import jakarta.validation.Valid;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@RequestMapping("/review")
@ComponentScan({"com.example.A2.service.ReviewService"} )
@RestController
public class ReviewController {
    private final ReviewService service;

    public ReviewController(ReviewService service) {
        this.service = service;
    }

    @GetMapping(path = "/{id}")
    public ReviewResponse findReviewById(@PathVariable Long id) { return service.get(id);}

    @GetMapping(path="/all")
    public List<ReviewResponse> getAllReviews() { return service.getAll();}

    @PostMapping(path = "/")
    public ReviewResponse addReview(@RequestBody @Valid ReviewRequest review)
    {
        return service.addService(review);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteReview(@PathVariable Long id) {
        service.deleteService(id);
    }

    @PutMapping(path = "/{id}")
    public void update(@PathVariable Long id, @RequestBody @Valid ReviewRequest review) {
        service.updateService(id, review);
    }

    @GetMapping(path = "/statProd")
    public List<ProductResponseLikes> getStatsProd() {
        return service.getStatisticalReportProducts();
    }

    @GetMapping(path = "/statCust")
    public List<CustomerResponseLikes> getStatsCust() {
        return service.getStatisticalReportCustomers();
    }
}
