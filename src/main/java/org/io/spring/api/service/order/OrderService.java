package org.io.spring.api.service.order;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.io.spring.api.controller.order.request.OrderCreateRequest;
import org.io.spring.api.controller.order.response.OrderResponse;
import org.io.spring.domain.order.Order;
import org.io.spring.domain.order.OrderRepository;
import org.io.spring.domain.product.Product;
import org.io.spring.domain.product.ProductRepository;
import org.io.spring.domain.product.ProductType;
import org.io.spring.domain.stock.Stock;
import org.io.spring.domain.stock.StockRepository;
import org.springframework.stereotype.Service;

@Transactional
@RequiredArgsConstructor
@Service
public class OrderService {

  private final ProductRepository productRepository;
  private final OrderRepository orderRepository;
  private final StockRepository stockRepository;

  public OrderResponse createOrder(OrderCreateRequest request, LocalDateTime registeredDateTime) {
    List<String> productNumbers = request.getProductNumbers();
    List<Product> products = findProductsBy(productNumbers); // 중복이 제거된 products

    // 재고 차감 체크가 필요한 상품들 filter
    List<String> stockProductNumbers = products.stream()
        .filter(product -> ProductType.containsStockType(product.getType()))
        .map(Product::getProductNumber)
        .toList();

    // 재고 엔티티 조회
    List<Stock> stocks = stockRepository.findAllByProductNumberIn(productNumbers);
    Map<String, Stock> stockMap = stocks.stream()
        .collect(toMap(Stock::getProductNumber, s -> s));

    // 상품별 counting
    Map<String, Long> productCountingMap = stockProductNumbers.stream()
        .collect(groupingBy(p -> p, counting()));

    // 재고 차감 시도
    for (String stockProductNumber : new HashSet<>(stockProductNumbers)) {
      Stock stock = stockMap.get(stockProductNumber);
      int quantity = productCountingMap.get(stockProductNumber).intValue();
      if (stock.isQuantityLessThan(quantity)) {
        throw new IllegalArgumentException("재고가 부족한 상품이 있습니다.");
      }
      stock.deductQuantity(quantity);
    }

    Order order = Order.create(products, registeredDateTime);
    Order savedOrder = orderRepository.save(order);
    return OrderResponse.of(savedOrder);
  }

  private List<Product> findProductsBy(List<String> productNumbers) {
    List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);
    Map<String, Product> productMap = products.stream()
        .collect(toMap(Product::getProductNumber, p -> p));

    return productNumbers.stream()
        .map(productMap::get)
        .toList();
  }

}
