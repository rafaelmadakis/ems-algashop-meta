package com.algaworks.algashop.ordering.domain.validator;

import java.util.Objects;
import org.apache.commons.validator.routines.EmailValidator;

public class FieldValidations {

  private FieldValidations() {

  }

  public static void requiresEmail(String email) {
    requiresEmail(email, null);

  }

  public static void requiresEmail(String email, String errorMessage) {
    Objects.requireNonNull(email, errorMessage);
    if (email.isEmpty()) {
      throw new IllegalArgumentException(errorMessage);
    }
    if (!EmailValidator.getInstance().isValid(email)) {
      throw new IllegalArgumentException(errorMessage);
    }


  }
}
