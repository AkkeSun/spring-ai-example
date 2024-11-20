package com.springaiexample.product.application.service;

import com.springaiexample.product.application.port.in.RegisterProductUseCase;
import com.springaiexample.product.application.port.in.command.RegisterProductCommand;
import com.springaiexample.product.application.port.out.GenerateEmbeddingPort;
import com.springaiexample.product.application.port.out.LanguageModelQueryPort;
import com.springaiexample.product.application.port.out.RegisterProductPort;
import com.springaiexample.product.domain.Product;
import dev.langchain4j.data.embedding.Embedding;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
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
            아래 여러 리뷰를 읽고, 구매자의 만족도와 감정을 기반으로 세 문장 이내로 요약해주세요.\s
            요약은 간결하게 작성하되, 긍정적 혹은 부정적인 감정을 명확히 드러내주세요.\s
            응답은 요약 텍스트만 포함해주세요.
            리뷰들: %s
            """, String.join(" ", command.reviews()));
        String reviewSummary = languageModelQueryPort.getQueryResponse(prompt);
        log.info("productId: {}, reviewSummary: {}", command.productId(), reviewSummary);

        prompt = String.format("""
            아래는 상품에 대한 정보입니다:
            - 상품명: %s
            - 설명: %s
            - 카테고리: %s
            - 가격: %d원
            - 리뷰 요약: %s
                          
            위 정보를 기반으로, 상품을 매력적이고 간결한 한 문단으로 설명해주세요.\s
            이 설명은 사용자가 검색할 때 추천 상품 목록으로 노출되기 위해 사용됩니다.\s
            따라서 상품의 장점과 느낌을 잘 담아야 합니다.\s
            문장은 **300자 이내로 작성**하고, 응답은 설명 텍스트만 포함해주세요.
              """, command.productName(), command.description(), command.category(), command.price(), reviewSummary);
        String productSummary = languageModelQueryPort.getQueryResponse(prompt);
        log.info("productId: {}, productSummary: {}", command.productId(), productSummary);

        // STEP 2 : data embedding
        Embedding embedding = generateEmbeddingPort.embeddingProduct(productSummary);

        // STEP 3: save
        String embeddingId = registerProductPort
            .registerProduct(productSummary, new Product().of(command), embedding);

        return RegisterProductServiceResponse.builder()
            .result("Y")
            .embeddingId(embeddingId)
            .build();
    }
}
