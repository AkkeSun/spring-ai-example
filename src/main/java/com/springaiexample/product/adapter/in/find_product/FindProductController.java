package com.springaiexample.product.adapter.in.find_product;

import com.springaiexample.product.application.port.out.FindProductPort;
import com.springaiexample.product.application.port.out.GenerateEmbeddingPort;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class FindProductController {

    private final FindProductPort findProductPort;
    private final GenerateEmbeddingPort generateEmbeddingPort;
    @GetMapping("/products")
    public void test(String query) {
        findProductPort.findByKeyword(generateEmbeddingPort.embeddingProduct(query));
    }
}
