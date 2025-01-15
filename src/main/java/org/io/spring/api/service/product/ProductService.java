package org.io.spring.api.service.product;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.io.spring.api.controller.product.request.ProductCreateRequest;
import org.io.spring.api.controller.product.response.ProductResponse;
import org.io.spring.domain.product.Product;
import org.io.spring.domain.product.ProductRepository;
import org.io.spring.domain.product.ProductSellingStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * readOnly = true : 읽기전용
 * CRUD 에서 CUD 동작 X / only Read
 * JPA : CUD 스냅샷 저장, 변경감지 X (성능 향상)
 *
 * CQRS - Command / Query
 */
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ProductService {

  private final ProductRepository productRepository;

  @Transactional
  public ProductResponse createProduct(ProductCreateServiceRequest request) {
    String nextProductNumber = createNextProductNumber();

    Product product = request.toEntity(nextProductNumber);
    Product savedProduct = productRepository.save(product);

    return ProductResponse.of(savedProduct);
  }

  private String createNextProductNumber() {
    String latestProductNumber = productRepository.findLatestProductNumber();
    if (latestProductNumber == null) {
      return "001";
    }

    int latestProductNumberInt = Integer.parseInt(latestProductNumber);
    int nextProductNumberInt = latestProductNumberInt + 1;

    // 9 -> 009, 10 -> 010
    return String.format("%03d", nextProductNumberInt);
  }

  public List<ProductResponse> getSellingProducts() {
    List<Product> products = productRepository.findAllBySellingStatusIn(
        ProductSellingStatus.forDisplay());

    return products.stream()
        .map(ProductResponse::of)
        .collect(Collectors.toList());
  }

}
