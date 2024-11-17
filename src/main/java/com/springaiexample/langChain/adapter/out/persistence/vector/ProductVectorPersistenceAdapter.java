package com.springaiexample.langChain.adapter.out.persistence.vector;

import com.springaiexample.langChain.application.port.out.FindProductPort;
import com.springaiexample.langChain.application.port.out.RegisterProductPort;
import com.springaiexample.langChain.domain.Product;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.store.embedding.chroma.ChromaEmbeddingStore;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductVectorPersistenceAdapter implements RegisterProductPort, FindProductPort {

    private final ProductMapper productMapper;
   // private final ChromaEmbeddingStore chromaEmbeddingStore;

    @Override
    public String registerProduct(String productSummary, Product product, Embedding embedding) {
        TextSegment segment = TextSegment.from(productSummary, productMapper.toMetadata(product));
        //     return chromaEmbeddingStore.add(embedding, segment);
        return null;
    }

    @Override
    public List<Product> findByKeyword(String keyword) {
        return null;
    }

    @Override
    public List<Product> findByKeywordAndCategory(String keyword, String category) {
        return null;
    }

}
