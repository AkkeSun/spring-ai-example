package com.springaiexample.product.adapter.out.persistence.vector;

import com.springaiexample.product.application.port.out.FindProductPort;
import com.springaiexample.product.application.port.out.RegisterProductPort;
import com.springaiexample.product.domain.Product;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.chroma.ChromaEmbeddingStore;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
        List<EmbeddingMatch<TextSegment>> relevant =
            chromaEmbeddingStore.findRelevant(embedding, 3, 0);
        EmbeddingMatch<TextSegment> embeddingMatch = relevant.get(0);
        System.out.println(embeddingMatch.score());
        String response = embeddingMatch.embedded().toString();
        System.out.println(response);
        return null;
    }

    @Override
    public List<Product> findByKeywordAndCategory(String keyword, String category) {
        return null;
    }
}
