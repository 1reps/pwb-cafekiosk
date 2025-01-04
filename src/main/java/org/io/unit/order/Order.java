package org.io.unit.order;

import java.time.LocalTime;
import java.util.List;
import lombok.Getter;
import org.io.unit.beverage.Beverage;

@Getter
public class Order {

  private LocalTime localTime;

  private List<Beverage> beverages;

  public Order(LocalTime localTime, List<Beverage> beverages) {
    this.localTime = localTime;
    this.beverages = beverages;
  }
}
