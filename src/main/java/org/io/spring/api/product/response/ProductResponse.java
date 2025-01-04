package org.io.spring.api.product.response;

import lombok.Builder;
import lombok.Getter;
import org.io.spring.domain.product.Product;
import org.io.spring.domain.product.ProductSellingStatus;
import org.io.spring.domain.product.ProductType;

@Getter
public class ProductResponse {

  private Long id;
  private String productNumber;
  private ProductType type;
  private ProductSellingStatus sellingStatus;
  private String name;
  private int price;

  // Builder로만 생성이 가능하게 유도
  @Builder
  private ProductResponse(Long id, String productNumber, ProductType type,
      ProductSellingStatus sellingStatus, String name, int price) {
    this.id = id;
    this.productNumber = productNumber;
    this.type = type;
    this.sellingStatus = sellingStatus;
    this.name = name;
    this.price = price;
  }

  public static ProductResponse of(Product product) {
    return ProductResponse.builder()
        .id(product.getId())
        .productNumber(product.getProductNumber())
        .type(product.getType())
        .sellingStatus(product.getSellingStatus())
        .name(product.getName())
        .price(product.getPrice())
        .build();
  }

}
