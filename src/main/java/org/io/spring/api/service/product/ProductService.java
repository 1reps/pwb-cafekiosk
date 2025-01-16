package org.io.spring.api.service.product;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.io.spring.api.controller.product.response.ProductResponse;
import org.io.spring.domain.product.Product;
import org.io.spring.domain.product.ProductRepository;
import org.io.spring.domain.product.ProductSellingStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * readOnly = true : 읽기전용 CRUD 에서 CUD 동작 X / only Read JPA : CUD 스냅샷 저장, 변경감지 X (성능 향상)
 * <p>
 * CQRS - Command / Query
 */
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductNumberFactory productNumberFactory;

    @Transactional
    public ProductResponse createProduct(ProductCreateServiceRequest request) {
        String nextProductNumber = productNumberFactory.createNextProductNumber();

        Product product = request.toEntity(nextProductNumber);
        Product savedProduct = productRepository.save(product);

        return ProductResponse.of(savedProduct);
    }

    /**
     * private는 테스트의 대상이 아니다, 테스트를 해야되는 상황이 발생하면 이미 설계가 잘못되었을 가능성이 크다.
     * Factory class를 만들어서 Factory 메서드의 테스트를 분리하자.
     */
    // private String createNextProductNumber() {
    //     String latestProductNumber = productRepository.findLatestProductNumber();
    //     if (latestProductNumber == null) {
    //         return "001";
    //     }
    //
    //     int latestProductNumberInt = Integer.parseInt(latestProductNumber);
    //     int nextProductNumberInt = latestProductNumberInt + 1;
    //
    //     // 9 -> 009, 10 -> 010
    //     return String.format("%03d", nextProductNumberInt);
    // }

    public List<ProductResponse> getSellingProducts() {
        List<Product> products = productRepository.findAllBySellingStatusIn(
            ProductSellingStatus.forDisplay());

        return products.stream()
            .map(ProductResponse::of)
            .collect(Collectors.toList());
    }

}
