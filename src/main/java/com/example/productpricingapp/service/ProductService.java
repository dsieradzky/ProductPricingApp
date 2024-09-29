package com.example.productpricingapp.service;

import com.example.productpricingapp.dto.PriceCalculationResponseDTO;
import com.example.productpricingapp.model.Product;

import java.util.UUID;

public interface ProductService {
    PriceCalculationResponseDTO calculatePrice(UUID productId, int quantity);
    Product addProduct(Product product);
    Product getProductById(UUID productId);
}

