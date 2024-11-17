package com.springaiexample.global.config;

import static java.time.Duration.ofSeconds;

import dev.langchain4j.store.embedding.chroma.ChromaEmbeddingStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChromaConfig {

    @Bean
    public ChromaEmbeddingStore chromaEmbeddingStore() {
        return ChromaEmbeddingStore.builder()
            .baseUrl("http://localhost:11434")
            .collectionName("test")
            .timeout(ofSeconds(60))
            .build();
    }
}
