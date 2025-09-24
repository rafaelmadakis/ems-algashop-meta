package com.algaworks.algashop.ordering.domain.valueobject;

import java.util.Objects;

public record ZipCode(String value) {

  public ZipCode {
    Objects.requireNonNull(value);
    if (value.isBlank()) {
      throw new IllegalArgumentException("Zip code cannot be blank");
    }
    if (value.length() != 5) {
      throw new IllegalArgumentException("Zip code must be 5 characters long");
    }
  }

  @Override
  public String toString() {
    return value;
  }
}
