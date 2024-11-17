package com.springaiexample.langChain.application.port.in;

import com.springaiexample.langChain.application.port.in.command.RegisterProductCommand;
import com.springaiexample.langChain.application.service.RegisterProductServiceResponse;

public interface RegisterProductUseCase {
    RegisterProductServiceResponse registerProduct(RegisterProductCommand command);
}
