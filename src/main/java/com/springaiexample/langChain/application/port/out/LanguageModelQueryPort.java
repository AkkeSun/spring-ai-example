package com.springaiexample.langChain.application.port.out;

public interface LanguageModelQueryPort {
    String getQueryResponse(String prompt);
}
