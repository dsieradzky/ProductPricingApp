package com.example.productpricingapp.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.productpricingapp.ProductPricingAppApplication;
import com.example.productpricingapp.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.*;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

import org.testcontainers.containers.GenericContainer;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@SpringBootTest(classes = ProductPricingAppApplication.class)
@Testcontainers
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ProductControllerIntegrationTest {

    @Container
    private static final GenericContainer<?> mongoDBContainer =
            new GenericContainer<>("mongo:latest").withExposedPorts(27017);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @DynamicPropertySource
    static void setMongoDbProperties(DynamicPropertyRegistry registry) {
        String mongoUri = String.format("mongodb://%s:%d/test",
                mongoDBContainer.getHost(),
                mongoDBContainer.getMappedPort(27017));
        registry.add("spring.data.mongodb.uri", () -> mongoUri);
    }

    @Test
    void shouldAddProductSuccessfully() throws Exception {
        Product product = new Product("Test Product", BigDecimal.valueOf(100.00));

        mockMvc.perform(post("/api/products")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test Product"))
                .andExpect(jsonPath("$.price").value(100.00));
    }

    @Test
    void shouldReturnProductWhenIdIsValid() throws Exception {
        Product product = new Product("Test Product", BigDecimal.valueOf(100.00));
        String productJson = objectMapper.writeValueAsString(product);

        mockMvc.perform(post("/api/products")
                        .contentType("application/json")
                        .content(productJson))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/products/{productId}", product.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Product"))
                .andExpect(jsonPath("$.price").value(100.00));
    }
}
