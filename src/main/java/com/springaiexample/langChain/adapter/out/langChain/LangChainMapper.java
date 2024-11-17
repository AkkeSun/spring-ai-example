package com.springaiexample.langChain.adapter.out.langChain;

import com.springaiexample.langChain.domain.Product;
import dev.langchain4j.data.document.Metadata;
import org.springframework.stereotype.Component;

@Component
public class LangChainMapper {
    public Metadata toMetadata(Product product) {
        Metadata metadata = new Metadata();
        metadata.put("productId", product.getProductId());
        metadata.put("productName", product.getProductName());
        metadata.put("category", product.getCategory());
        metadata.put("description", product.getDescription());
        metadata.put("price", product.getPrice());
        metadata.put("reviewSummary", product.getReviewSummary());
        return metadata;
    }
}
