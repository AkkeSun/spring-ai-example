package com.springaiexample.product.application.port.out;

import com.springaiexample.product.domain.Product;
import dev.langchain4j.data.embedding.Embedding;
import java.util.List;

public interface FindProductPort {
    List<Product> findByKeyword(Embedding embedding);
    List<Product> findByKeywordAndCategory(String keyword, String category);
}
