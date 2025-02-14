package org.io.spring.domain.order;

import static org.io.spring.domain.order.OrderStatus.INIT;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.io.spring.domain.BaseEntity;
import org.io.spring.domain.orderproduct.OrderProduct;
import org.io.spring.domain.product.Product;

@Entity
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
@Getter
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private int totalPrice;

    private LocalDateTime registeredDateTime;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    @Builder
    public Order(List<Product> products, OrderStatus orderStatus, LocalDateTime registeredDateTime) {
        this.orderStatus = orderStatus;
        this.totalPrice = calculateTotalPrice(products);
        this.registeredDateTime = registeredDateTime;
        this.orderProducts = products.stream()
            .map(product -> new OrderProduct(this, product))
            .collect(Collectors.toList());
    }

    public static Order create(List<Product> products, LocalDateTime registeredDateTime) {
        return Order.builder()
            .orderStatus(INIT)
            .products(products)
            .registeredDateTime(registeredDateTime)
            .build();
    }

    private static int calculateTotalPrice(List<Product> products) {
        return products.stream()
            .mapToInt(Product::getPrice)
            .sum();
    }
}
