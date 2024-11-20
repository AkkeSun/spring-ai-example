package com.springaiexample.product.application.port.in;

import com.springaiexample.product.application.port.in.command.RegisterProductCommand;
import com.springaiexample.product.application.service.RegisterProductServiceResponse;

public interface RegisterProductUseCase {
    RegisterProductServiceResponse registerProduct(RegisterProductCommand command);
}
