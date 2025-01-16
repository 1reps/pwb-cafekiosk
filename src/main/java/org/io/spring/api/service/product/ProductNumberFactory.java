package org.io.spring.api.service.product;

import lombok.RequiredArgsConstructor;
import org.io.spring.domain.product.ProductRepository;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ProductNumberFactory {

    private final ProductRepository productRepository;

    public String createNextProductNumber() {
        String latestProductNumber = productRepository.findLatestProductNumber();
        if (latestProductNumber == null) {
            return "001";
        }

        int latestProductNumberInt = Integer.parseInt(latestProductNumber);
        int nextProductNumberInt = latestProductNumberInt + 1;

        // 9 -> 009, 10 -> 010
        return String.format("%03d", nextProductNumberInt);
    }

}
