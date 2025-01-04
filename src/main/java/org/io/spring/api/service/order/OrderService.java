package org.io.spring.api.service.order;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.io.spring.api.controller.order.request.OrderCreateRequest;
import org.io.spring.api.controller.order.response.OrderResponse;
import org.io.spring.domain.order.Order;
import org.io.spring.domain.order.OrderRepository;
import org.io.spring.domain.product.Product;
import org.io.spring.domain.product.ProductRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderService {

  private final ProductRepository productRepository;
  private final OrderRepository orderRepository;

  public OrderResponse createOrder(OrderCreateRequest request, LocalDateTime registeredDateTime) {
    List<String> productNumbers = request.getProductNumbers();
    List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);

    Order order = Order.create(products, registeredDateTime);
    Order savedOrder = orderRepository.save(order);
    return OrderResponse.of(savedOrder);
  }

}
