package com.springaiexample.product.application.port.out;

import dev.langchain4j.data.embedding.Embedding;

public interface GenerateEmbeddingPort {
    Embedding embeddingProduct(String productSummary);
}
