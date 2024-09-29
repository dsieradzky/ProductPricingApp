package com.example.productpricingapp.service;

import java.math.BigDecimal;

public interface DiscountService {
    BigDecimal calculateDiscount(BigDecimal price, int quantity);
}

