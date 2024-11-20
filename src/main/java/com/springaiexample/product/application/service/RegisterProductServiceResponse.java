package com.springaiexample.product.application.service;

import lombok.Builder;

@Builder
public record RegisterProductServiceResponse(
    String result,
    String embeddingId
) {

}
