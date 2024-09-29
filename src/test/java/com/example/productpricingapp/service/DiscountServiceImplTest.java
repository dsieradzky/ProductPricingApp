package com.example.productpricingapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyInt;

import com.example.productpricingapp.model.AmountBasedDiscountPolicy;
import com.example.productpricingapp.model.PercentageBasedDiscountPolicy;
import com.example.productpricingapp.service.impl.DiscountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;


class DiscountServiceImplTest {

    @Mock
    private DiscountPolicyService discountPolicyService;

    @InjectMocks
    private DiscountServiceImpl discountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @CsvSource({
            "100.00, 10, 10.00",
            "200.00, 20, 20.00"
    })
    void shouldReturnCorrectDiscountWhenPoliciesArePresent(BigDecimal price, int quantity, BigDecimal expectedDiscount) {
        AmountBasedDiscountPolicy amountPolicy = new AmountBasedDiscountPolicy(10, BigDecimal.valueOf(10.00));
        PercentageBasedDiscountPolicy percentagePolicy = new PercentageBasedDiscountPolicy(20, BigDecimal.valueOf(10.00));

        when(discountPolicyService.getApplicablePolicies(anyInt())).thenReturn(List.of(amountPolicy, percentagePolicy));

        BigDecimal discount = discountService.calculateDiscount(price, quantity);

        assertEquals(expectedDiscount.setScale(2), discount.setScale(2));
    }

    @Test
    void shouldReturnZeroDiscountWhenNoPoliciesApply() {
        when(discountPolicyService.getApplicablePolicies(anyInt())).thenReturn(Collections.emptyList());

        BigDecimal discount = discountService.calculateDiscount(BigDecimal.valueOf(100.00), 5);

        assertEquals(BigDecimal.ZERO.setScale(2), discount.setScale(2));
    }
}