package com.springaiexample.langChain.application.service;

import com.springaiexample.langChain.application.port.in.RegisterProductUseCase;
import com.springaiexample.langChain.application.port.in.command.RegisterProductCommand;
import com.springaiexample.langChain.application.port.out.GenerateEmbeddingPort;
import com.springaiexample.langChain.application.port.out.LanguageModelQueryPort;
import com.springaiexample.langChain.application.port.out.RegisterProductPort;
import com.springaiexample.langChain.domain.Product;
import dev.langchain4j.data.embedding.Embedding;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class RegisterProductService implements RegisterProductUseCase {

    private final LanguageModelQueryPort languageModelQueryPort;
    private final GenerateEmbeddingPort generateEmbeddingPort;
    private final RegisterProductPort registerProductPort;

    @Override
    public RegisterProductServiceResponse registerProduct(RegisterProductCommand command) {
        // STEP 1 : make embedding target data
        String prompt = String.format("""
            아래 여러 리뷰의 정보를 모두 합쳐서 세 문장 이내로 문장으로 요약해주세요.
            구매자의 만족도와 느낌을 위주로 담아주세요.
            답만 말해주세요.
            리뷰들: %s
            """, String.join(" ", command.reviews()));
        String reviewSummary = languageModelQueryPort.getQueryResponse(prompt);

        prompt = String.format("""
              상품에 대한 정보를 아래와 같이 제공합니다:
              - 상품명: %s
              - 설명: %s
              - 카테고리: %s
              - 가격: %d
              - 리뷰 요약: %s
            
              위 정보를 바탕으로 상품을 간결하고 매력적인 문장으로 설명해주세요.
              문장의 길이는 **300자 이내**로 작성해 주세요.
              답만 말해주세요.
              이 문장은 사용자가 키워드 검색을 할 때 추천 상품 목록으로 노출되기 위해 임베딩하여 벡터 DB에 저장될 예정입니다.
             
              """, command.productName(), command.description(), command.category(), command.price(), reviewSummary);
        String productSummary = languageModelQueryPort.getQueryResponse(prompt);

        // TODO: 토큰 분리 어떻게 함?
        // STEP 2 : data embedding
        Product product = new Product().of(command, reviewSummary);
        Embedding embedding = generateEmbeddingPort.embeddingProduct(productSummary, product);

        // STEP 3: save
        String embeddingId = registerProductPort
            .registerProduct(productSummary, product, embedding);

        return RegisterProductServiceResponse.builder()
            .result("Y")
            .embeddingId(embeddingId)
            .build();
    }
}
