package com.example.productpricingapp.controller;

import com.example.productpricingapp.dto.CreateDiscountPolicyRequest;
import com.example.productpricingapp.model.DiscountPolicy;
import com.example.productpricingapp.service.DiscountPolicyService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/api/discounts")
public class DiscountPolicyController {

    private final DiscountPolicyService discountPolicyService;

    public DiscountPolicyController(DiscountPolicyService discountPolicyService) {
        this.discountPolicyService = discountPolicyService;
    }

    @Operation(summary = "Create a new discount policy")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Discount policy created successfully",
                    content = @Content(schema = @Schema(implementation = DiscountPolicy.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DiscountPolicy createDiscountPolicy(@Valid @RequestBody CreateDiscountPolicyRequest request) {
        return discountPolicyService.createDiscountPolicy(request);
    }

    @Operation(summary = "Get all discount policies")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetched all discount policies",
                    content = @Content(schema = @Schema(implementation = DiscountPolicy.class)))
    })
    @GetMapping
    public List<DiscountPolicy> getAllDiscountPolicies() {
        return discountPolicyService.getAllPolicies();
    }
}
