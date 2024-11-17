package com.springaiexample.langChain.domain;

import com.springaiexample.langChain.application.port.in.command.RegisterProductCommand;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Product {

    private Long productId;
    private String productName;
    private String category;
    private String description;
    private int price;
    private String reviewSummary;

    @Builder
    public Product(Long productId, String productName, String category, String description,
        int price, String reviewSummary) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.description = description;
        this.price = price;
        this.reviewSummary = reviewSummary;
    }

    public Product of(RegisterProductCommand command, String reviewSummary) {
        return Product.builder()
            .productId(command.productId())
            .productName(command.productName())
            .category(command.category())
            .description(command.description())
            .price(command.price())
            .reviewSummary(reviewSummary)
            .build();
    }
}
