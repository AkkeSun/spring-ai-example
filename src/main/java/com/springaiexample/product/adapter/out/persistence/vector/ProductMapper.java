package com.springaiexample.product.adapter.out.persistence.vector;

import com.springaiexample.product.domain.Product;
import dev.langchain4j.data.document.Metadata;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public Metadata toMetadata(Product product) {
        Metadata metadata = new Metadata();
        metadata.put("productId", product.getProductId());
        metadata.put("productName", product.getProductName());
        metadata.put("category", product.getCategory());
        metadata.put("description", product.getDescription());
        metadata.put("price", product.getPrice());
        return metadata;
    }

    public Product toDomain(Metadata metadata) {
        return Product.builder()
            .productId(metadata.getLong("productId"))
            .productName(metadata.getString("productName"))
            .category(metadata.getString("category"))
            .description(metadata.getString("description"))
            .price(metadata.getInteger("price"))
            .build();
    }

}
