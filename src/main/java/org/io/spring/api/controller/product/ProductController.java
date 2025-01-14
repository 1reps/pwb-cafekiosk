package org.io.spring.api.controller.product;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.io.spring.api.controller.product.request.ProductCreateRequest;
import org.io.spring.api.controller.product.response.ProductResponse;
import org.io.spring.api.service.product.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController("/api/v1/products")
public class ProductController {

  private final ProductService productService;

  @PostMapping("/new")
  public void createProduct(ProductCreateRequest request) {
    productService.createProduct(request);

  }

  @GetMapping("/selling")
  public List<ProductResponse> getSellingProducts() {
    return productService.getSellingProducts();
  }

}
