package com.springaiexample.product.application.port.in.command;

import java.util.List;
import lombok.Builder;

@Builder
public record RegisterProductCommand(
    Long productId,
    String productName,
    String description,
    String category,
    int price,
    List<String> reviews
) {

}
