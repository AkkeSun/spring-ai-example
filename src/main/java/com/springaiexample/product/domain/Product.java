package com.springaiexample.product.domain;

import com.springaiexample.product.application.port.in.command.RegisterProductCommand;
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

    @Builder
    public Product(Long productId, String productName, String category, String description,
        int price) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.description = description;
        this.price = price;
    }

    public Product of(RegisterProductCommand command) {
        return Product.builder()
            .productId(command.productId())
            .productName(command.productName())
            .category(command.category())
            .description(command.description())
            .price(command.price())
            .build();
    }
}
