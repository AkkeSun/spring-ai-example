package com.springaiexample.langChain;

import static dev.langchain4j.internal.Utils.randomUUID;
import static dev.langchain4j.store.embedding.filter.MetadataFilterBuilder.metadataKey;
import static java.time.Duration.ofSeconds;

import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.allminilml6v2.AllMiniLmL6V2EmbeddingModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingSearchResult;
import dev.langchain4j.store.embedding.chroma.ChromaEmbeddingStore;
import dev.langchain4j.store.embedding.filter.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

// https://devloo.tistory.com/entry/Spring-AI-Spring-Boot%EC%99%80-%EB%8C%80%ED%98%95-%EC%96%B8%EC%96%B4-%EB%AA%A8%EB%8D%B8LLM%EC%9D%98-%EC%89%AC%EC%9A%B4-%EC%97%B0%EB%8F%99-%EB%B0%A9%EB%B2%95
@RequiredArgsConstructor
@RestController
public class Ollama {

    public static void main(String[] args) {
        // STEP 1 : languageModel 을 통한 자연어 처리
        OllamaChatModel languageModel = OllamaChatModel.builder()
            .baseUrl("http://localhost:11434")
            .modelName("llama2")
            .temperature(0.8) // 생성된 응답의 무작위성을 제어합니다. 값이 높을수록(예: 1.0) 출력이 더 다양해지고, 값이 낮을수록(예: 0.2) 더 결정론적인 응답이 생성됩니다.
            .timeout(ofSeconds(60))
            .build();
        String generateResult = languageModel.generate("hello");

        // STEP 2 : 데이터 임베딩
        EmbeddingModel embeddingModel = new AllMiniLmL6V2EmbeddingModel();
        Metadata metadata = new Metadata();
        metadata.put("request_id", randomUUID());
        metadata.put("user_id", "user123");

        TextSegment segment = TextSegment.from("I like football.", metadata);
        Embedding embedding = embeddingModel.embed(segment).content();

        // STEP 3: 임베딩 저장
        ChromaEmbeddingStore embeddingStore = ChromaEmbeddingStore.builder()
            .baseUrl("http://localhost:11434")
            .collectionName("test")
            .timeout(ofSeconds(60))
            .build();
        String saveResult = embeddingStore.add(embedding, segment);

        // STEP 4: 임베딩 검색
        Embedding queryEmbedding = embeddingModel.embed("What is your favourite sport?").content();
        Filter filter = metadataKey("userId").isEqualTo("1");

        EmbeddingSearchRequest searchRequest = EmbeddingSearchRequest
            .builder()
            .queryEmbedding(queryEmbedding)
            .filter(filter)
            .build();

        EmbeddingSearchResult<TextSegment> result = embeddingStore.search(searchRequest);
        EmbeddingMatch<TextSegment> match = result.matches().get(0);
        System.out.println(match.score());
        System.out.println(match.embedded().text());
        System.out.println(match.embedded().metadata());
    }
}
