package com.springaiexample.langChain.adapter.out.langChain;

import com.springaiexample.langChain.application.port.out.GenerateEmbeddingPort;
import com.springaiexample.langChain.application.port.out.LanguageModelQueryPort;
import com.springaiexample.langChain.domain.Product;
import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class LangChainPersistenceAdapter implements GenerateEmbeddingPort, LanguageModelQueryPort {

    private final LangChainMapper langChainMapper;
    private final OllamaChatModel ollamaChatModel;
    private final EmbeddingModel allMiniLmL6V2EmbeddingModel;

    @Override
    public String getQueryResponse(String prompt) {
        return ollamaChatModel.generate(prompt);
    }

    @Override
    public Embedding embeddingProduct(String productSummary, Product product) {
        Metadata metadata = langChainMapper.toMetadata(product);
        TextSegment segment = TextSegment.from(productSummary, metadata);
        return allMiniLmL6V2EmbeddingModel.embed(segment).content();
    }
}
