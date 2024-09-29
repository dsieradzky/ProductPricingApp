package com.example.productpricingapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import com.example.productpricingapp.dto.CreateDiscountPolicyRequest;
import com.example.productpricingapp.mapper.DiscountPolicyMapper;
import com.example.productpricingapp.model.AmountBasedDiscountPolicy;
import com.example.productpricingapp.model.DiscountPolicy;
import com.example.productpricingapp.repository.DiscountPolicyRepository;
import com.example.productpricingapp.service.impl.DiscountPolicyServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

class DiscountPolicyServiceImplTest {

    @Mock
    private DiscountPolicyRepository discountPolicyRepository;

    @Mock
    private DiscountPolicyMapper discountPolicyMapper;

    @InjectMocks
    private DiscountPolicyServiceImpl discountPolicyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateAmountBasedDiscountPolicySuccessfully() {
        CreateDiscountPolicyRequest request = new CreateDiscountPolicyRequest(10, BigDecimal.valueOf(5.00), null);
        AmountBasedDiscountPolicy policy = new AmountBasedDiscountPolicy(10, BigDecimal.valueOf(5.00));

        when(discountPolicyMapper.toAmountBasedEntity(any(CreateDiscountPolicyRequest.class))).thenReturn(policy);
        when(discountPolicyRepository.save(any(DiscountPolicy.class))).thenReturn(policy);

        DiscountPolicy result = discountPolicyService.createDiscountPolicy(request);

        assertEquals(policy.getQuantityThreshold(), result.getQuantityThreshold());
        assertEquals(policy.getDiscountAmount(), ((AmountBasedDiscountPolicy) result).getDiscountAmount());
    }

    @Test
    void shouldThrowExceptionWhenBothDiscountFieldsAreNull() {
        CreateDiscountPolicyRequest request = new CreateDiscountPolicyRequest(10, null, null);

        assertThrows(IllegalArgumentException.class, () -> discountPolicyService.createDiscountPolicy(request));
    }
}
