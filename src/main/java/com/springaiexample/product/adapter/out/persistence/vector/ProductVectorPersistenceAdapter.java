package com.springaiexample.product.adapter.out.persistence.vector;

import com.springaiexample.product.application.port.out.FindProductPort;
import com.springaiexample.product.application.port.out.RegisterProductPort;
import com.springaiexample.product.domain.Product;
import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.chroma.ChromaEmbeddingStore;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductVectorPersistenceAdapter implements RegisterProductPort, FindProductPort {

    private final ProductMapper productMapper;
    private final ChromaEmbeddingStore chromaEmbeddingStore;

    @Override
    public String registerProduct(String productSummary, Product product, Embedding embedding) {
        TextSegment segment = TextSegment.from(productSummary, productMapper.toMetadata(product));
        return chromaEmbeddingStore.add(embedding, segment);
    }

    @Override
    public List<Product> findByKeyword(Embedding embedding) {
        List<EmbeddingMatch<TextSegment>> results =
            chromaEmbeddingStore.findRelevant(embedding, 3, 0);

        return results.stream()
            .map(result -> {
                log.info("score: {}, metadata: {}", result.score(), result.embedded().metadata());
                return productMapper.toDomain(result.embedded().metadata());
            })
            .collect(Collectors.toList());
    }

    @Override
    public List<Product> findByKeywordAndCategory(String keyword, String category) {
        return null;
    }
}
