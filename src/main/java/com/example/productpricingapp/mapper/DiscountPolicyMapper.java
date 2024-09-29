package com.example.productpricingapp.mapper;

import com.example.productpricingapp.dto.CreateDiscountPolicyRequest;
import com.example.productpricingapp.model.AmountBasedDiscountPolicy;
import com.example.productpricingapp.model.PercentageBasedDiscountPolicy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DiscountPolicyMapper {

    @Mapping(target = "id", ignore = true)
    AmountBasedDiscountPolicy toAmountBasedEntity(CreateDiscountPolicyRequest request);

    @Mapping(target = "id", ignore = true)
    PercentageBasedDiscountPolicy toPercentageBasedEntity(CreateDiscountPolicyRequest request);
}

