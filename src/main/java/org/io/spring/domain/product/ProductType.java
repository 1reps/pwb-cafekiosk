package org.io.spring.product;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ProductType {

  HANDMADTE("제조 음료"),
  BOTTEL("병 음료"),
  BAKERY("베이커리");

  private final String text;

}
