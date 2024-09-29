package com.example.productpricingapp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@Document(collection = "discount_policies")
public abstract class DiscountPolicy {
    @Id
    private String id;

    @NotNull
    private int quantityThreshold;

    public DiscountPolicy(int quantityThreshold) {
        this.quantityThreshold = quantityThreshold;
    }
}
