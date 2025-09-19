package com.algaworks.algashop.ordering.domain.entity;


import com.algaworks.algashop.ordering.domain.utilility.IdGenerator;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class CustomerTest {

  @Test
  void given_invalidEmail_whenTryCreateCustomer_shouldGenerateException() {

    Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> {
          new Customer(
              IdGenerator.generateTimeBaseUUID(),
              "John Doe",
              LocalDate.of(1991, 7, 5),
              "invalid",
              "345-7675-333",
              "123.456.789-00",
              false,
              OffsetDateTime.now()
          );
        });
  }

  @Test
  void given_invalidEmail_whenTryUpdateCustomerEmail_shouldGenerateException() {
    Customer customer = new Customer(
        IdGenerator.generateTimeBaseUUID(),
        "John Doe",
        LocalDate.of(1991, 7, 5),
        "joho.doe@email.com",
        "345-7675-333",
        "123.456.789-00",
        false,
        OffsetDateTime.now()
    );
    Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> {
          customer.changeEmail("invalid");
        });
  }
}