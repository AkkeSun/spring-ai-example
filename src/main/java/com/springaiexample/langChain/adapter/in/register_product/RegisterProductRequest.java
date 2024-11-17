package com.springaiexample.langChain.adapter.in.register_product;

import com.springaiexample.langChain.application.port.in.command.RegisterProductCommand;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
class RegisterProductRequest {
    private Long productId;
    private String productName;
    private String category;
    private String description;
    private int price;
    private List<String> reviews;

    @Builder
    RegisterProductRequest(Long productId, String productName, String category,
        String description, int price, List<String> reviews) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.description = description;
        this.price = price;
        this.reviews = reviews;
    }

    RegisterProductCommand toCommand(){
        return RegisterProductCommand.builder()
            .productId(productId)
            .productName(productName)
            .category(category)
            .description(description)
            .price(price)
            .reviews(reviews)
            .build();
    }
}
