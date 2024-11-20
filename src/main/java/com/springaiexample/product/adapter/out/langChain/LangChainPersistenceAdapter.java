package com.springaiexample.product.adapter.out.langChain;

import static java.time.Duration.ofSeconds;

import com.springaiexample.product.application.port.out.GenerateEmbeddingPort;
import com.springaiexample.product.application.port.out.LanguageModelQueryPort;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class LangChainPersistenceAdapter implements GenerateEmbeddingPort, LanguageModelQueryPort {

    private final LangChainMapper langChainMapper;
    private final OllamaChatModel ollamaChatModel;
    private final UpstageModel upstageModel;
    private final EmbeddingModel allMiniLmL6V2EmbeddingModel;

    @Override
    public String getQueryResponse(String prompt) {
        // return ollamaChatModel.generate(prompt);
        return upstageModel.generate(prompt);
    }

    @Override
    public Embedding embeddingProduct(String productSummary) {
        return allMiniLmL6V2EmbeddingModel.embed(productSummary).content();
    }
}