package com.example.productpricingapp.dto;

import java.util.UUID;

public record PriceCalculationRequestDTO(
        UUID productId,
        int quantity
) {}
