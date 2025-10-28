package com.algaworks.algashop.ordering.domain.entity;


import com.algaworks.algashop.ordering.domain.exception.OrderInvalidShippingDeliveryDateException;
import com.algaworks.algashop.ordering.domain.exception.OrderStatusCannotBeChangedException;
import com.algaworks.algashop.ordering.domain.valueobject.Address;
import com.algaworks.algashop.ordering.domain.valueobject.BillingInfo;
import com.algaworks.algashop.ordering.domain.valueobject.Document;
import com.algaworks.algashop.ordering.domain.valueobject.FullName;
import com.algaworks.algashop.ordering.domain.valueobject.Money;
import com.algaworks.algashop.ordering.domain.valueobject.Phone;
import com.algaworks.algashop.ordering.domain.valueobject.ProductName;
import com.algaworks.algashop.ordering.domain.valueobject.Quantity;
import com.algaworks.algashop.ordering.domain.valueobject.ShippingInfo;
import com.algaworks.algashop.ordering.domain.valueobject.ZipCode;
import com.algaworks.algashop.ordering.domain.valueobject.id.CustomerId;
import com.algaworks.algashop.ordering.domain.valueobject.id.ProductId;
import java.time.LocalDate;
import java.util.Set;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class OrderTest {

  @Test
  public void shouldGenerate() {
    Order order = Order.draft(new CustomerId());
  }

  @Test
  public void shouldAddItem() {
    Order order = Order.draft(new CustomerId());

    ProductId productId = new ProductId();
    order.addItem(
        productId,
        new ProductName("Mouse Pad"),
        new Money("100"),
        new Quantity(1)
    );

    Assertions.assertThat(order.items().size()).isEqualTo(1);

    OrderItem orderItem = order.items().iterator().next();

    Assertions.assertWith(orderItem,
        (i) -> Assertions.assertThat(i.id()).isNotNull(),
        (i) -> Assertions.assertThat(i.productName()).isEqualTo(new ProductName("Mouse Pad")),
        (i) -> Assertions.assertThat(i.productId()).isEqualTo(productId),
        (i) -> Assertions.assertThat(i.price()).isEqualTo(new Money("100")),
        (i) -> Assertions.assertThat(i.quantity()).isEqualTo(new Quantity(1))
    );
  }


  @Test
  public void shouldGenerationExceptionWhenTryToChangeItemSet() {
    Order order = Order.draft(new CustomerId());

    ProductId productId = new ProductId();
    order.addItem(
        productId,
        new ProductName("Mouse Pad"),
        new Money("100"),
        new Quantity(1)
    );

    Set<OrderItem> items = order.items();

    Assertions.assertThatExceptionOfType(UnsupportedOperationException.class)
        .isThrownBy(items::clear);

  }

  @Test
  public void shouldCalculatetotals() {
    Order order = Order.draft(new CustomerId());

    ProductId productId = new ProductId();
    order.addItem(
        productId,
        new ProductName("Mouse Pad"),
        new Money("100"),
        new Quantity(2)
    );
    order.addItem(
        productId,
        new ProductName("RAM memory"),
        new Money("50"),
        new Quantity(1)
    );

    Assertions.assertThat(order.totalAmount()).isEqualTo(new Money("250"));
    Assertions.assertThat(order.totalItems()).isEqualTo(new Quantity(3));

  }

  @Test
  public void givenDraftOrder_whenPlaced_shouldChangeToPlaced() {
    Order order = Order.draft(new CustomerId());
    order.place();

    Assertions.assertThat(order.isPlaced()).isTrue();
  }

  @Test
  public void givenPlacedOrder_whenTryToPlace_shouldGenerateException() {
    Order order = Order.draft(new CustomerId());
    order.place();

    Assertions.assertThatExceptionOfType(OrderStatusCannotBeChangedException.class)
        .isThrownBy(order::place);
  }

  @Test
  public void givenDraftOrder_whenChangePaymentMethod_shouldAllowChange() {
    Order order = Order.draft(new CustomerId());

    order.changePaymentMethod(PaymentMethod.CREDIT_CARD);
    Assertions.assertWith(order.paymentMethod()).isEqualTo(PaymentMethod.CREDIT_CARD);
  }

  @Test
  public void givenDraftOrder_whenChangeBillinginfo_shouldAllowChange() {

    Address addressBuilder = Address.builder()
        .street("Bourboun Street")
        .number("1234")
        .neighborhood("North Ville")
        .complement("apt. 11")
        .city("Montfort")
        .state("South Caroline")
        .zipCode(new ZipCode("79911"))
        .build();

    BillingInfo billingInfo = BillingInfo.builder()

        .address(addressBuilder)
        .document(new Document("225-09-1992"))
        .phone(new Phone("123-111-9991"))
        .fullName(new FullName("John", "Doe"))
        .build();

    Order order = Order.draft(new CustomerId());
    order.changeBilling(billingInfo);

    BillingInfo expectedBillingInfo = BillingInfo.builder()

        .address(addressBuilder)
        .document(new Document("225-09-1992"))
        .phone(new Phone("123-111-9991"))
        .fullName(new FullName("John", "Doe"))
        .build();

    Assertions.assertThat(order.billing()).isEqualTo(billingInfo);
  }

  @Test
  public void givenDraftOrder_whenChangeShippingInfo_shouldAllowChange() {

    Address addressBuilder = Address.builder()
        .street("Bourboun Street")
        .number("1234")
        .neighborhood("North Ville")
        .complement("apt. 11")
        .city("Montfort")
        .state("South Caroline")
        .zipCode(new ZipCode("79911"))
        .build();

    ShippingInfo shippingInfo = ShippingInfo.builder()
        .address(addressBuilder)
        .fullName(new FullName("Jane", "Doe"))
        .document(new Document("112-33-2321"))
        .phone(new Phone("111-441-1244"))
        .build();

    Order order = Order.draft(new CustomerId());
    Money shippingCost = Money.ZERO;
    LocalDate expectedDeliveryDate = LocalDate.now().plusDays(2);
    order.changeShipping(shippingInfo, shippingCost, expectedDeliveryDate);

    Assertions.assertWith(order,
        o -> Assertions.assertThat(o.shipping()).isEqualTo(shippingInfo),
        o -> Assertions.assertThat(o.shippingCost()).isEqualTo(shippingCost),
        o -> Assertions.assertThat(o.expectedDeliveryDate()).isEqualTo(expectedDeliveryDate));

  }


  @Test
  public void givenDraftOrderAndDeliveryDateInThePast_whenChangeShippingInfo_shouldNotAllowChange() {

    Address addressBuilder = Address.builder()
        .street("Bourboun Street")
        .number("1234")
        .neighborhood("North Ville")
        .complement("apt. 11")
        .city("Montfort")
        .state("South Caroline")
        .zipCode(new ZipCode("79911"))
        .build();

    ShippingInfo shippingInfo = ShippingInfo.builder()
        .address(addressBuilder)
        .fullName(new FullName("Jane", "Doe"))
        .document(new Document("112-33-2321"))
        .phone(new Phone("111-441-1244"))
        .build();

    Order order = Order.draft(new CustomerId());
    Money shippingCost = Money.ZERO;

    LocalDate expectedDeliveryDate = LocalDate.now().minusDays(2);

    Assertions.assertThatExceptionOfType(OrderInvalidShippingDeliveryDateException.class)
        .isThrownBy(() -> order.changeShipping(shippingInfo, shippingCost, expectedDeliveryDate));


  }

}