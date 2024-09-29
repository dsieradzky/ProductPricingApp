package com.example.productpricingapp.dto;

import com.example.productpricingapp.validator.OneDiscountOnly;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@OneDiscountOnly
public record CreateDiscountPolicyRequest(
        @NotNull int quantityThreshold,
        BigDecimal discountAmount,
        BigDecimal discountPercentage
) {}
