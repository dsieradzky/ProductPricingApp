package com.example.productpricingapp.model;

import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@Document(collection = "discount_policies")
public class AmountBasedDiscountPolicy extends DiscountPolicy {
    @NotNull
    private BigDecimal discountAmount;

    public AmountBasedDiscountPolicy(int quantityThreshold, BigDecimal discountAmount) {
        super(quantityThreshold);
        this.discountAmount = discountAmount;
    }
}
