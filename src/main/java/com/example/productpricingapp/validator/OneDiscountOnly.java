package com.example.productpricingapp.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = OneDiscountOnlyValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface OneDiscountOnly {
    String message() default "Either discountAmount or discountPercentage must be provided, but not both.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
