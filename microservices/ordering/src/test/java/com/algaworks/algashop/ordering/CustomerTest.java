package com.algaworks.algashop.ordering;

import com.algaworks.algashop.ordering.domain.entity.Customer;
import com.algaworks.algashop.ordering.domain.utilility.IdGenerator;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.Test;


public class CustomerTest {

  @Test
  public void testingCustomer() {
    Customer customer = new Customer(
        IdGenerator.generateTimeBaseUUID(),
        "Jhon Doe",
        LocalDate.of(1991,7,5),
        "jhon.doe@email.com",
        "478-256-1234",
        "255-8-0578",
        true,
        OffsetDateTime.now()
    );

    System.out.println(customer.id());
    System.out.println(IdGenerator.generateTimeBaseUUID());

    customer.addLoyaltyPoints(10);



  }

}
