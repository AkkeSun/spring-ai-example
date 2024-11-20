package com.springaiexample.product.adapter.in.register_product;

import com.springaiexample.product.application.port.in.RegisterProductUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class RegisterProductController {

    private final RegisterProductUseCase registerProductUseCase;

    @PostMapping("/products")
    public void test(@RequestBody RegisterProductRequest request) {
        registerProductUseCase.registerProduct(request.toCommand());

    }
}
