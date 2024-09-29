package com.example.productpricingapp.service.impl;

import com.example.productpricingapp.model.AmountBasedDiscountPolicy;
import com.example.productpricingapp.model.DiscountPolicy;
import com.example.productpricingapp.model.PercentageBasedDiscountPolicy;
import com.example.productpricingapp.service.DiscountPolicyService;
import com.example.productpricingapp.service.DiscountService;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DiscountServiceImpl implements DiscountService {

    private final DiscountPolicyService discountPolicyService;

    public DiscountServiceImpl(DiscountPolicyService discountPolicyService) {
        this.discountPolicyService = discountPolicyService;
    }

    @Override
    public BigDecimal calculateDiscount(BigDecimal price, int quantity) {
        log.info("Calculating discount for quantity: {} and price: {}", quantity, price);
        List<DiscountPolicy> policies = discountPolicyService.getApplicablePolicies(quantity);
        BigDecimal maxDiscount = BigDecimal.ZERO;

        for (DiscountPolicy policy : policies) {
            BigDecimal discount = BigDecimal.ZERO;
            if (policy instanceof AmountBasedDiscountPolicy) {
                discount = ((AmountBasedDiscountPolicy) policy).getDiscountAmount();
            } else if (policy instanceof PercentageBasedDiscountPolicy) {
                discount = price.multiply(((PercentageBasedDiscountPolicy) policy).getDiscountPercentage().divide(BigDecimal.valueOf(100)));
            }
            maxDiscount = maxDiscount.max(discount);
        }

        log.info("Calculated max discount: {}", maxDiscount);
        return maxDiscount;
    }
}
