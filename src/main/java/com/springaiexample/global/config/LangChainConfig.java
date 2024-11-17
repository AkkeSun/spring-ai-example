package com.springaiexample.global.config;

import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.allminilml6v2.AllMiniLmL6V2EmbeddingModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class LangChainConfig {

    @Bean
    public OllamaChatModel ollamaChatModel() {
        return OllamaChatModel.builder()
            .baseUrl("http://localhost:11434")
            .modelName("llama2")
            .temperature(0.8)
            .build();
    }

    @Bean
    public EmbeddingModel allMiniLmL6V2EmbeddingModel() {
        return new AllMiniLmL6V2EmbeddingModel();
    }
}
