package com.example.productpricingapp.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import com.example.productpricingapp.dto.CreateDiscountPolicyRequest;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.ConstraintValidatorContext.ConstraintViolationBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class OneDiscountOnlyValidatorTest {

    private OneDiscountOnlyValidator validator;
    private ConstraintValidatorContext context;

    @BeforeEach
    void setUp() {
        validator = new OneDiscountOnlyValidator();
        context = mock(ConstraintValidatorContext.class);
        ConstraintViolationBuilder violationBuilder = mock(ConstraintViolationBuilder.class);

        when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(violationBuilder);
    }

    @Test
    void shouldReturnTrueWhenOnlyAmountIsPresent() {
        CreateDiscountPolicyRequest request = new CreateDiscountPolicyRequest(10, BigDecimal.valueOf(5.00), null);
        assertTrue(validator.isValid(request, context));
    }

    @Test
    void shouldReturnTrueWhenOnlyPercentageIsPresent() {
        CreateDiscountPolicyRequest request = new CreateDiscountPolicyRequest(10, null, BigDecimal.valueOf(10.00));
        assertTrue(validator.isValid(request, context));
    }

    @Test
    void shouldReturnFalseWhenBothAmountAndPercentageArePresent() {
        CreateDiscountPolicyRequest request = new CreateDiscountPolicyRequest(10, BigDecimal.valueOf(5.00), BigDecimal.valueOf(10.00));

        boolean result = validator.isValid(request, context);
        assertFalse(result);

        verify(context).disableDefaultConstraintViolation();
        verify(context).buildConstraintViolationWithTemplate("Either discountAmount or discountPercentage must be provided, but not both.");
    }

    @Test
    void shouldReturnFalseWhenNeitherAmountNorPercentageIsPresent() {
        CreateDiscountPolicyRequest request = new CreateDiscountPolicyRequest(10, null, null);

        boolean result = validator.isValid(request, context);
        assertFalse(result);

        verify(context).disableDefaultConstraintViolation();
        verify(context).buildConstraintViolationWithTemplate("Either discountAmount or discountPercentage must be provided, but not both.");
    }
}
