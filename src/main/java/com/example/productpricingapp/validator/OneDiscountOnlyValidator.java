package com.example.productpricingapp.validator;

import com.example.productpricingapp.dto.CreateDiscountPolicyRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class OneDiscountOnlyValidator implements ConstraintValidator<OneDiscountOnly, CreateDiscountPolicyRequest> {

    @Override
    public boolean isValid(CreateDiscountPolicyRequest request, ConstraintValidatorContext context) {
        boolean isAmountPresent = request.discountAmount() != null;
        boolean isPercentagePresent = request.discountPercentage() != null;

        boolean isValid = isAmountPresent ^ isPercentagePresent;

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                            "Either discountAmount or discountPercentage must be provided, but not both.")
                    .addConstraintViolation();
        }

        return isValid;
    }
}
