package com.example.productpricingapp.repository;

import com.example.productpricingapp.model.DiscountPolicy;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscountPolicyRepository extends MongoRepository<DiscountPolicy, String> {
    List<DiscountPolicy> findByQuantityThresholdLessThanEqual(int quantity);
}
