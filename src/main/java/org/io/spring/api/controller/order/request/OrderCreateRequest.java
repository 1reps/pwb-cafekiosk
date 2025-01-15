package org.io.spring.api.controller.order.request;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.io.spring.api.service.order.OrderCreateServiceRequest;

@Getter
@NoArgsConstructor
public class OrderCreateRequest {

  @NotEmpty(message = "상품 번호 리스트는 필수입니다.")
  private List<String> productNumbers;

  @Builder
  private OrderCreateRequest(List<String> productNumbers) {
    this.productNumbers = productNumbers;
  }

  public OrderCreateServiceRequest toServiceRequest() {
    return OrderCreateServiceRequest.builder()
        .productNumbers(productNumbers)
        .build();
  }
}
