package com.example.A2.service;

import com.example.A2.domain.Customer;
import com.example.A2.domain.Product;
import com.example.A2.domain.Review;

import com.example.A2.domain.dto.*;
import com.example.A2.domain.mappers.CustomerMapper;
import com.example.A2.domain.mappers.ProductMapper;
import com.example.A2.domain.mappers.ReviewMapper;
import com.example.A2.repository.CustomerRepository;
import com.example.A2.repository.ProductRepository;
import com.example.A2.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final CustomerRepository customerRepository;

    private final ProductRepository productRepository;

    private final ReviewMapper reviewMapper;

    private final ProductMapper productMapper;
    private final CustomerMapper customerMapper;

    public ReviewResponse get(Long id) {
        if (reviewRepository.existsById(id))
        {
            Review review = reviewRepository.findById(id).get();
            return mapReviewResponse(review);
        }
        return null;
    }


    public ReviewResponse addService(ReviewRequest reviewRequest) {
        Optional<Customer> optionalCustomer = customerRepository.findById(reviewRequest.getIdCustomer());
        Optional<Product> optionalProduct = productRepository.findById(reviewRequest.getIdProduct());
        Review review = reviewMapper.map(reviewRequest);

        if (optionalProduct.isPresent()) review.setProduct(optionalProduct.get());
        else return null;
        if (optionalCustomer.isPresent()) review.setCustomer(optionalCustomer.get());
        else return null;

        reviewRepository.save(review);

        customerRepository.save(optionalCustomer.get());
        productRepository.save(optionalProduct.get());

        return mapReviewResponse(review);
    }


    public List<ReviewResponse> getAll() {
        List<Review> reviews = reviewRepository.findAll();
        List<ReviewResponse> reviewResponses = new ArrayList<>();
        for (Review r : reviews)
        {
            ReviewResponse reviewResponse = mapReviewResponse(r);
            reviewResponses.add(reviewResponse);
        }
        return reviewResponses;
    }


    public void deleteService(Long id) {
        if (reviewRepository.existsById(id))
        {
            reviewRepository.deleteById(id);
        }
    }


    public void updateService(Long id, ReviewRequest entity) {
        Review review = reviewRepository.findById(id).
                orElseThrow(() -> new IllegalStateException("There is no such entity in the database"));

        Customer customer = customerRepository.getReferenceById(entity.getIdCustomer());
        Product product = productRepository.getReferenceById(entity.getIdProduct());
        review.setProduct(product);
        review.setCustomer(customer);
        review.setReviewText(entity.getReviewText());
        review.setCreatedAt(entity.getCreatedAt());
        review.setNumberLikes(entity.getNumberLikes());

        reviewRepository.save(review);
    }

    private ReviewResponse mapReviewResponse(Review review)
    {
        ReviewResponse reviewResponse = new ReviewResponse();
        reviewResponse.setId(review.getId());
        reviewResponse.setIdCustomer(review.getCustomer().getId());
        reviewResponse.setIdProduct(review.getProduct().getId());
        reviewResponse.setReviewText(review.getReviewText());
        reviewResponse.setCreatedAt(review.getCreatedAt());
        reviewResponse.setNumberLikes( review.getNumberLikes());
        return reviewResponse;
    }

    // show all the products ordered by the average of their review likes
    // for each product => avg(reviewLikes)
    public List<ProductResponseLikes> getStatisticalReportProducts()
    {
        List<Product> products = productRepository.findAll();
        List<ProductResponseLikes> result = new ArrayList<>();

        List<Review> reviews = reviewRepository.findAll();
        for (Product p : products)
        {
            double s=0,count=0;
            for (Review r : reviews)
            {
                if (Objects.equals(r.getProduct().getId(), p.getId())) {
                    s += r.getNumberLikes();
                    count++;
                }
            }
            ProductResponseLikes pr = new ProductResponseLikes();
            pr.setId(p.getId());
            pr.setName(p.getName());
            if (s > 0 && count > 0)
            {
                Double avg = s / count;
                pr.setLikes(avg);
                result.add(pr);
            }
        }
        return result;
    }


    // how all the customers ordered by the average of their review likes
    public List<CustomerResponseLikes> getStatisticalReportCustomers()
    {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerResponseLikes> result = new ArrayList<>();

        List<Review> reviews = reviewRepository.findAll();
        for (Customer customer : customers)
        {
            double sum=0,count=0;
            for (Review review : reviews)
            {
                if (Objects.equals(review.getCustomer().getId(), customer.getId())) {
                    sum += review.getNumberLikes();
                    count++;
                }
            }
            CustomerResponseLikes customerResponse = new CustomerResponseLikes();
            customerResponse.setId(customer.getId());
            customerResponse.setFirstName(customer.getFirstName());
            customerResponse.setLastName(customer.getLastName());
            if (sum > 0 && count > 0)
            {
                Double avg = sum / count;
                customerResponse.setLikes(avg);
                result.add(customerResponse);
            }
        }
        return result;
    }


}
