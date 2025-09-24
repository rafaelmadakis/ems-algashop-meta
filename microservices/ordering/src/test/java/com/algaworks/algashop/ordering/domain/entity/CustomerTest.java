package com.algaworks.algashop.ordering.domain.entity;


import static org.assertj.core.api.Assertions.*;

import com.algaworks.algashop.ordering.domain.exception.CustomerArchivedException;
import com.algaworks.algashop.ordering.domain.utilility.IdGenerator;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class CustomerTest {

  @Test
  void given_invalidEmail_whenTryCreateCustomer_shouldGenerateException() {

    assertThatExceptionOfType(IllegalArgumentException.class)
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
    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> {
          customer.changeEmail("invalid");
        });
  }

  @Test
  void given_unarchivedCustomer_whenArchive_shouldAnonymize() {

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

    customer.archive();

    assertWith(customer,
        c -> assertThat(c.fullName()).isEqualTo("Anonymous"),
        c -> assertThat(c.email()).isNotEqualTo("joho.doe@email.com"),
        c -> assertThat(c.phone()).isEqualTo("000-000-0000"),
        c -> assertThat(c.document()).isEqualTo("000.000.0000"),
        c -> assertThat(c.birthDate()).isNull(),
        c -> assertThat(c.isPromotionNotificationAllowed()).isFalse()
    );

  }

  @Test
  void given_archivedCustomer_whenTryToUpdate_shouldGeneratedException() {
    Customer customer = new Customer(
        IdGenerator.generateTimeBaseUUID(),
        "Anomymous",
        null,
        "anonymous@anonymous.com",
        "000-000-000",
        "000.000.000-00",
        false,
        true,
        OffsetDateTime.now(),
        OffsetDateTime.now(),
        10
    );

    Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
        .isThrownBy(()-> customer.archive());

    Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
        .isThrownBy(()-> customer.changeEmail("email@email.com"));

    Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
        .isThrownBy(()-> customer.changePhone("123-123-1111"));

    Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
        .isThrownBy(()-> customer.enablePromotionNotifications());

    Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
        .isThrownBy(()-> customer.disablePromotionNotifications());

  }

  @Test
  void given_brandNewCustomer_whenAddLoyaltyPoints_shouldSumPoints() {

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

    customer.addLoyaltyPoints(10);
    customer.addLoyaltyPoints(20);

    Assertions.assertThat(customer.loyaltyPoints()).isEqualTo(30);

  }

  @Test
  void given_brandNewCustomer_whenAddInvalidPoints_shouldGenerateException() {

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
        .isThrownBy(()-> customer.addLoyaltyPoints(0));

    Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(()-> customer.addLoyaltyPoints(-10));

  }
}