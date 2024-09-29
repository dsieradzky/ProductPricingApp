package com.example.productpricingapp.service.impl;

import com.example.productpricingapp.dto.PriceCalculationResponseDTO;
import com.example.productpricingapp.exception.ProductNotFoundException;
import com.example.productpricingapp.model.Product;
import com.example.productpricingapp.repository.ProductRepository;
import com.example.productpricingapp.service.DiscountService;
import com.example.productpricingapp.service.ProductService;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final DiscountService discountService;

    public ProductServiceImpl(ProductRepository productRepository, DiscountService discountService) {
        this.productRepository = productRepository;
        this.discountService = discountService;
    }

    @Override
    public PriceCalculationResponseDTO calculatePrice(UUID productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found: " + productId));

        BigDecimal discount = discountService.calculateDiscount(product.getPrice(), quantity);
        BigDecimal totalPrice = product.getPrice().multiply(BigDecimal.valueOf(quantity)).subtract(discount);

        return new PriceCalculationResponseDTO(totalPrice, discount);
    }

    @Override
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product getProductById(UUID productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found: " + productId));
    }
}
