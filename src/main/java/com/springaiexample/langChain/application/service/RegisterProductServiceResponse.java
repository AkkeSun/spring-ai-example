package com.springaiexample.langChain.application.service;

import lombok.Builder;

@Builder
public record RegisterProductServiceResponse(
    String result,
    String embeddingId
) {

}
