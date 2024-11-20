package com.springaiexample.product.application.port.out;

import com.springaiexample.product.domain.Product;
import dev.langchain4j.data.embedding.Embedding;

public interface RegisterProductPort {
    String registerProduct(String productSummary, Product product, Embedding embedding);
}
