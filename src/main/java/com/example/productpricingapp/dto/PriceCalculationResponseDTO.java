package com.example.productpricingapp.dto;

import java.math.BigDecimal;

public record PriceCalculationResponseDTO(
        BigDecimal totalPrice,
        BigDecimal discountApplied
) {}

