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

    public OrderResponse createOrder(OrderCreateServiceRequest request, LocalDateTime registeredDateTime) {
        List<String> productNumbers = request.getProductNumbers();
        List<Product> products = findProductsBy(productNumbers); // 중복이 제거된 products

        deductStockQuantities(products, productNumbers);

        Order order = Order.create(products, registeredDateTime);
        Order savedOrder = orderRepository.save(order);
        return OrderResponse.of(savedOrder);
    }

    private void deductStockQuantities(List<Product> products, List<String> productNumbers) {
        List<String> stockProductNumbers = extractStockProductNumbers(products);

        Map<String, Stock> stockMap = createStockMapBy(productNumbers);
        Map<String, Long> productCountingMap = createCountingMapBy(stockProductNumbers);

        HashSet<String> distinctStockProductNumbers = new HashSet<>(stockProductNumbers);
        for (String stockProductNumber : distinctStockProductNumbers) {
            Stock stock = stockMap.get(stockProductNumber);
            int quantity = productCountingMap.get(stockProductNumber).intValue();

            if (stock.isQuantityLessThan(quantity)) {
                throw new IllegalArgumentException("재고가 부족한 상품이 있습니다.");
            }
            stock.deductQuantity(quantity);
        }
    }

    private static Map<String, Long> createCountingMapBy(List<String> stockProductNumbers) {
        return stockProductNumbers.stream()
            .collect(groupingBy(p -> p, counting()));
    }

    private Map<String, Stock> createStockMapBy(List<String> productNumbers) {
        List<Stock> stocks = stockRepository.findAllByProductNumberIn(productNumbers);
        return stocks.stream()
            .collect(toMap(Stock::getProductNumber, s -> s));
    }

    private static List<String> extractStockProductNumbers(List<Product> products) {
        return products.stream()
            .filter(product -> ProductType.containsStockType(product.getType()))
            .map(Product::getProductNumber)
            .toList();
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
