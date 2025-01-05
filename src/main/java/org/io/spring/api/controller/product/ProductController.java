package org.io.spring.api.controller.product;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.io.spring.api.service.product.ProductService;
import org.io.spring.api.controller.product.response.ProductResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ProductController {

  private final ProductService productService;

  @GetMapping("/api/v1/products/selling")
  public List<ProductResponse> getSellingProducts() {
    return productService.getSellingProducts();
  }

}
