package org.io.spring.api.controller.order.response;

import static java.util.stream.Collectors.toList;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.io.spring.api.controller.product.response.ProductResponse;
import org.io.spring.domain.order.Order;

@Getter
public class OrderResponse {

    private Long id;
    private int totalPrice;
    private LocalDateTime registeredDateTime;
    private List<ProductResponse> products;

    @Builder
    private OrderResponse(Long id, int totalPrice, LocalDateTime registeredDateTime,
        List<ProductResponse> products) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.registeredDateTime = registeredDateTime;
        this.products = products;
    }

    public static OrderResponse of(Order order) {
        return OrderResponse.builder()
            .id(order.getId())
            .totalPrice(order.getTotalPrice())
            .registeredDateTime(order.getRegisteredDateTime())
            .products(order.getOrderProducts().stream()
                .map(orderProduct -> ProductResponse.of(orderProduct.getProduct()))
                .collect(toList())
            )
            .build();
    }

}
