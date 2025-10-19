package com.algaworks.algashop.ordering.domain.valueobject.id;

import com.algaworks.algashop.ordering.domain.utilility.IdGenerator;
import java.util.Objects;
import java.util.UUID;

public record CustomerId(UUID value) {

  public CustomerId() {
    this(IdGenerator.generateTimeBaseUUID());
  }

  public CustomerId(UUID value) {
    Objects.requireNonNull(value);
    this.value = value;
  }

  @Override
  public String toString() {
    return value.toString();
  }
}
