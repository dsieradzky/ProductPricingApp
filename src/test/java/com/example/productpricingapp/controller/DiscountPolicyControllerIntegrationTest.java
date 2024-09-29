package com.example.productpricingapp.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import com.example.productpricingapp.dto.CreateDiscountPolicyRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

import org.testcontainers.containers.GenericContainer;


import com.example.productpricingapp.ProductPricingAppApplication;

@SpringBootTest(classes = ProductPricingAppApplication.class)
@Testcontainers
@AutoConfigureMockMvc
class DiscountPolicyControllerIntegrationTest {

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
    void shouldCreateAmountBasedDiscountPolicySuccessfully() throws Exception {
        CreateDiscountPolicyRequest request = new CreateDiscountPolicyRequest(10, BigDecimal.valueOf(5.00), null);

        mockMvc.perform(post("/api/discounts")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.quantityThreshold").value(10))
                .andExpect(jsonPath("$.discountAmount").value(5.00));
    }

    @Test
    void shouldFetchAllDiscountPolicies() throws Exception {
        CreateDiscountPolicyRequest request = new CreateDiscountPolicyRequest(10, BigDecimal.valueOf(5.00), null);

        mockMvc.perform(post("/api/discounts")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/discounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].quantityThreshold").value(10))
                .andExpect(jsonPath("$[0].discountAmount").value(5.00));
    }
}
