package com.example.productpricingapp.service;

import com.example.productpricingapp.dto.CreateDiscountPolicyRequest;
import com.example.productpricingapp.model.DiscountPolicy;

import java.util.List;

public interface DiscountPolicyService {
    List<DiscountPolicy> getApplicablePolicies(int quantity);
    DiscountPolicy createDiscountPolicy(CreateDiscountPolicyRequest request);
    List<DiscountPolicy> getAllPolicies();
}
