package org.io.spring.api.controller.product.request;

import lombok.Builder;
import lombok.Getter;
import org.io.spring.domain.product.Product;
import org.io.spring.domain.product.ProductSellingStatus;
import org.io.spring.domain.product.ProductType;

@Getter
public class ProductCreateRequest {

  private ProductType type;
  private ProductSellingStatus sellingStatus;
  private String name;
  private int price;

  @Builder
  private ProductCreateRequest(ProductType type, ProductSellingStatus sellingStatus, String name,
      int price) {
    this.type = type;
    this.sellingStatus = sellingStatus;
    this.name = name;
    this.price = price;
  }

  public Product toEntity(String nextProductNumber) {
    return Product.builder()
        .productNumber(nextProductNumber)
        .type(type)
        .sellingStatus(sellingStatus)
        .name(name)
        .price(price)
        .build();
  }
}
