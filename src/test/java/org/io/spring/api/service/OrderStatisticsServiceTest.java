package org.io.spring.api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.io.spring.domain.order.OrderStatus.PAYMENT_COMPLETED;
import static org.io.spring.domain.product.ProductSellingStatus.SELLING;
import static org.io.spring.domain.product.ProductType.HANDMADE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.io.spring.client.MailSendClient;
import org.io.spring.domain.history.MailSendHistory;
import org.io.spring.domain.history.MailSendHistoryRepository;
import org.io.spring.domain.order.Order;
import org.io.spring.domain.order.OrderRepository;
import org.io.spring.domain.orderproduct.OrderProductRepository;
import org.io.spring.domain.product.Product;
import org.io.spring.domain.product.ProductRepository;
import org.io.spring.domain.product.ProductType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
class OrderStatisticsServiceTest {

    @Autowired
    private OrderStatisticsService orderStatisticsService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MailSendHistoryRepository mailSendHistoryRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @MockitoBean
    private MailSendClient mailSendClient;

    @AfterEach
    void tearDown() {
        orderProductRepository.deleteAllInBatch();
        orderRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        mailSendHistoryRepository.deleteAllInBatch();
    }

    @DisplayName("결제완료 주문들을 조회하여 매출 통계 메일을 전송한다.")
    @Test
    void sendOrderStatisticsMail() {
        // given
        LocalDateTime now = LocalDateTime.now();

        Product product1 = createProduct(HANDMADE, "001", 1000);
        Product product2 = createProduct(HANDMADE, "002", 2000);
        Product product3 = createProduct(HANDMADE, "003", 3000);
        List<Product> products = List.of(product1, product2, product3);
        productRepository.saveAll(products);

        Order order1 = createPaymentCompletedOrder(LocalDateTime.of(2025, 1, 4, 23, 59, 59), products);
        Order order2 = createPaymentCompletedOrder(now, products);
        Order order3 = createPaymentCompletedOrder(LocalDateTime.of(2025, 1, 6, 0, 0, 0), products);
        Order order4 = createPaymentCompletedOrder(LocalDateTime.of(2025, 1, 7, 0, 0, 0), products);

        // stubbing
        when(mailSendClient.sendEmail(any(String.class),
            any(String.class),
            any(String.class),
            any(String.class)))
            .thenReturn(true);

        // when
        boolean result = orderStatisticsService.sendOrderStatisticsMail(LocalDate.of(2025, 1, 15), "test@test.com");

        // then
        assertThat(result).isTrue();

        List<MailSendHistory> histories = mailSendHistoryRepository.findAll();
        assertThat(histories).hasSize(1)
            .extracting("content")
            .contains("총 매출 합계는 6000원입니다.");
    }

    private Order createPaymentCompletedOrder(LocalDateTime registeredDateTime, List<Product> products) {
        Order order = Order.builder()
            .orderStatus(PAYMENT_COMPLETED)
            .products(products)
            .registeredDateTime(registeredDateTime)
            .build();
        return orderRepository.save(order);
    }

    private Product createProduct(ProductType type, String productNumber, int price) {
        return Product.builder()
            .productNumber(productNumber)
            .type(type)
            .sellingStatus(SELLING)
            .name("메뉴 이름")
            .price(price)
            .build();
    }

}