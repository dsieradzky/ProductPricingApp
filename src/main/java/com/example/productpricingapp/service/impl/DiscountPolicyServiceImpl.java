package com.example.productpricingapp.service.impl;

import com.example.productpricingapp.dto.CreateDiscountPolicyRequest;
import com.example.productpricingapp.mapper.DiscountPolicyMapper;
import com.example.productpricingapp.model.DiscountPolicy;
import com.example.productpricingapp.repository.DiscountPolicyRepository;
import com.example.productpricingapp.service.DiscountPolicyService;
import org.springframework.stereotype.Service;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DiscountPolicyServiceImpl implements DiscountPolicyService {

    private final DiscountPolicyRepository discountPolicyRepository;
    private final DiscountPolicyMapper discountPolicyMapper;

    public DiscountPolicyServiceImpl(DiscountPolicyRepository discountPolicyRepository, DiscountPolicyMapper discountPolicyMapper) {
        this.discountPolicyRepository = discountPolicyRepository;
        this.discountPolicyMapper = discountPolicyMapper;
    }

    @Override
    public List<DiscountPolicy> getApplicablePolicies(int quantity) {
        log.info("Fetching applicable discount policies for quantity: {}", quantity);
        return discountPolicyRepository.findByQuantityThresholdLessThanEqual(quantity);
    }

    @Override
    public DiscountPolicy createDiscountPolicy(CreateDiscountPolicyRequest request) {
        DiscountPolicy policy;
        if (request.discountAmount() != null) {
            policy = discountPolicyMapper.toAmountBasedEntity(request);
        } else if (request.discountPercentage() != null) {
            policy = discountPolicyMapper.toPercentageBasedEntity(request);
        } else {
            throw new IllegalArgumentException("Either discountAmount or discountPercentage must be provided.");
        }

        log.info("Creating new discount policy with threshold: {}", request.quantityThreshold());
        return discountPolicyRepository.save(policy);
    }

    @Override
    public List<DiscountPolicy> getAllPolicies() {
        log.info("Fetching all discount policies");
        return discountPolicyRepository.findAll();
    }
}
