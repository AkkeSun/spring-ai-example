package com.springaiexample.langChain.application.port.out;

import com.springaiexample.langChain.domain.Product;
import dev.langchain4j.data.embedding.Embedding;

public interface RegisterProductPort {
    String registerProduct(String productSummary, Product product, Embedding embedding);
}
