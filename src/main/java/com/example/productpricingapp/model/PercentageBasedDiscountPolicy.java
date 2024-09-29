package com.example.productpricingapp.model;

import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@Document(collection = "discount_policies")
public class PercentageBasedDiscountPolicy extends DiscountPolicy {
    @NotNull
    private BigDecimal discountPercentage;

    public PercentageBasedDiscountPolicy(int quantityThreshold, BigDecimal discountPercentage) {
        super(quantityThreshold);
        this.discountPercentage = discountPercentage;
    }
}
