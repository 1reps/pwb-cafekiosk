package org.io.spring.api.controller.order;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.io.spring.api.controller.order.request.OrderCreateRequest;
import org.io.spring.api.service.order.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class OrderController {

  private final OrderService orderService;

  @PostMapping("/api/v1/orders/new")
  public void createOrder(@RequestBody OrderCreateRequest request, LocalDateTime registeredDateTime) {
    orderService.createOrder(request, registeredDateTime);
  }

}
