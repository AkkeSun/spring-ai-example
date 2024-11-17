package com.springaiexample.langChain.application.port.out;

import com.springaiexample.langChain.domain.Product;
import java.util.List;

public interface FindProductPort {
    List<Product> findByKeyword(String keyword);
    List<Product> findByKeywordAndCategory(String keyword, String category);
}
