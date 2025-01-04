package org.io.unit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import org.io.unit.beverage.Americano;
import org.io.unit.beverage.Latte;
import org.io.unit.order.Order;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CafeKioskTest {

  @Test
  void getNameWithPrice() {
    CafeKiosk cafeKiosk = new CafeKiosk();
    Americano americano = new Americano();

    cafeKiosk.add(americano, 1);

    assertThat(cafeKiosk.getBeverages().get(0).getName()).isEqualTo("아메리카노");
    assertThat(cafeKiosk.getBeverages().get(0).getPrice()).isEqualTo(3000);
  }

  @DisplayName("음료 1개 추가하면 주문 목록에 담긴다.")
  @Test
  void add() {
    CafeKiosk cafeKiosk = new CafeKiosk();
    Americano americano = new Americano();

    cafeKiosk.add(americano, 1);

    assertThat(cafeKiosk.getBeverages()).hasSize(1);
    assertThat(cafeKiosk.getBeverages().get(0).getName()).isEqualTo("아메리카노");
  }

  @Test
  void addSeveralBeverages() {
    CafeKiosk cafeKiosk = new CafeKiosk();
    Americano americano = new Americano();

    cafeKiosk.add(americano, 2);

    assertThat(cafeKiosk.getBeverages().get(0)).isEqualTo(americano);
    assertThat(cafeKiosk.getBeverages().get(1)).isEqualTo(americano);
  }

  @Test
  void addZeroBeverages() {
    CafeKiosk cafeKiosk = new CafeKiosk();
    Americano americano = new Americano();

    assertThatThrownBy(() -> cafeKiosk.add(americano, 0))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("음료는 1잔 이상 주문하실 수 있습니다.");
  }

  @Test
  void remove() {
    CafeKiosk cafeKiosk = new CafeKiosk();
    cafeKiosk.remove(new Americano());

    assertThat(cafeKiosk.getBeverages()).isEmpty();
  }

  @DisplayName("주문 목록에 담긴 상품들의 총 금액을 계산할 수 있다.")
  @Test
  void calculateTotalPrice() {
    // given
    CafeKiosk cafeKiosk = new CafeKiosk();
    Americano americano = new Americano();
    Latte latte = new Latte();

    cafeKiosk.add(americano);
    cafeKiosk.add(latte);

    // when
    int totalPrice = cafeKiosk.calculateTotalPrice();

    // then
    assertThat(totalPrice).isEqualTo(6500);
  }

  @Test
  void createOrderWithCurrentTime() {
    CafeKiosk cafeKiosk = new CafeKiosk();
    Americano americano = new Americano();

    cafeKiosk.add(americano, 1);

    Order order = cafeKiosk.createOrder(LocalDateTime.of(2025, 1, 3, 10, 0));

    assertThat(order.getBeverages()).hasSize(1);
    assertThat(order.getBeverages().get(0).getName()).isEqualTo("아메리카노");
  }

  @Test
  void createOrderOutsideOpenTime() {
    CafeKiosk cafeKiosk = new CafeKiosk();
    Americano americano = new Americano();

    cafeKiosk.add(americano, 1);

    assertThatThrownBy(() ->
        cafeKiosk.createOrder(LocalDateTime.of(2025, 1, 4, 9, 0)))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("주문 시간이 아닙니다. 관리자에게 문의하세요.");
  }

}