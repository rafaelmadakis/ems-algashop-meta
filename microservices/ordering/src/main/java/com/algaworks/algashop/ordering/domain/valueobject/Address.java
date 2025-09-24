package com.algaworks.algashop.ordering.domain.valueobject;

import com.algaworks.algashop.ordering.domain.validator.FieldValidations;
import java.util.Objects;
import lombok.Builder;


public record Address(
    String street,
    String complement,
    String neighborhood,
    String number,
    String city,
    String state,
    ZipCode zipCode) {

  @Builder(toBuilder = true)
  public Address {
    FieldValidations.requiredNonBlank(street);
    FieldValidations.requiredNonBlank(neighborhood);
    FieldValidations.requiredNonBlank(city);
    FieldValidations.requiredNonBlank(number);
    FieldValidations.requiredNonBlank(state);
    Objects.requireNonNull(zipCode);
  }

}
