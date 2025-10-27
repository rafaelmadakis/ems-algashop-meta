package com.algaworks.algashop.ordering.domain.exception;

import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.ERROR_ORDER_STATUS_CANNOT_BE_CHANGED;

import com.algaworks.algashop.ordering.domain.entity.OrderStatus;
import com.algaworks.algashop.ordering.domain.valueobject.id.OrderId;

public class OrderStatusCannotBeChangedException  extends DomainException {


  public OrderStatusCannotBeChangedException(OrderId id, OrderStatus status, OrderStatus newStatus) {
    super(String.format(ERROR_ORDER_STATUS_CANNOT_BE_CHANGED, id, status, newStatus));
  }
}
