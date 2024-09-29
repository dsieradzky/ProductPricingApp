package com.example.productpricingapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

import com.example.productpricingapp.dto.PriceCalculationResponseDTO;
import com.example.productpricingapp.exception.ProductNotFoundException;
import com.example.productpricingapp.model.Product;
import com.example.productpricingapp.repository.ProductRepository;
import com.example.productpricingapp.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private DiscountService discountService;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCalculatePriceWithDiscountWhenProductExists() {
        Product product = new Product("Test Product", BigDecimal.valueOf(100.00));
        when(productRepository.findById(any(UUID.class))).thenReturn(Optional.of(product));
        when(discountService.calculateDiscount(any(BigDecimal.class), anyInt())).thenReturn(BigDecimal.valueOf(10.00));

        PriceCalculationResponseDTO response = productService.calculatePrice(UUID.randomUUID(), 5);

        assertEquals(BigDecimal.valueOf(490.00), response.totalPrice());
        assertEquals(BigDecimal.valueOf(10.00), response.discountApplied());
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {
        when(productRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.calculatePrice(UUID.randomUUID(), 5));
    }
}
