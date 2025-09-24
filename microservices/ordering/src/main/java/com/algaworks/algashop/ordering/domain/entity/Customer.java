package com.algaworks.algashop.ordering.domain.entity;

import static com.algaworks.algashop.ordering.domain.exception.ErroMessages.VALIDATION_ERROR_BIRTHDATE_MUST_IN_PAST;
import static com.algaworks.algashop.ordering.domain.exception.ErroMessages.VALIDATION_ERROR_EMAIL_IS_INVALID;
import static com.algaworks.algashop.ordering.domain.exception.ErroMessages.VALIDATION_ERROR_FULLNAME_IS_BLANK;
import static com.algaworks.algashop.ordering.domain.exception.ErroMessages.VALIDATION_ERROR_FULLNAME_IS_NULL;

import com.algaworks.algashop.ordering.domain.validator.FieldValidations;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;


public class Customer {

  private UUID id;
  private String fullName;
  private LocalDate birthDate;
  private String email;
  private String phone;
  private String document;
  private Boolean promotionNotificationAllowed;
  private Boolean archived;
  private OffsetDateTime registeredAt;
  private OffsetDateTime archivedAt;
  private Integer loyaltyPoints;


  public Customer(UUID id, String fullName, LocalDate birthDate, String email,
      String phone, String document, Boolean promotionNotificationAllowed,
      OffsetDateTime registeredAt) {
    this.setId(id);
    this.setFullName(fullName);
    this.setBirthDate(birthDate);
    this.setEmail(email);
    this.setPhone(phone);
    this.setDocument(document);
    this.setPromotionNotificationAllowed(promotionNotificationAllowed);
    this.setRegisteredAt(registeredAt);
    this.setArchived(false);
    this.setLoyaltyPoints(0);
  }

  public Customer(UUID id, String fullName, LocalDate birthDate, String email,
      String phone, String document, Boolean promotionNotificationAllowed,
      Boolean archived, OffsetDateTime registeredAt, OffsetDateTime archivedAt, Integer loyaltyPoints) {

    this.id = id;
    this.fullName = fullName;
    this.birthDate = birthDate;
    this.email = email;
    this.phone = phone;
    this.document = document;
    this.promotionNotificationAllowed = promotionNotificationAllowed;
    this.archived = archived;
    this.registeredAt = registeredAt;
    this.archivedAt = archivedAt;
    this.loyaltyPoints = loyaltyPoints;
  }

  public void addLoyaltyPoints(Integer points) {

  }

  public void archieve() {
    this.setArchived(true);
    this.setArchivedAt(OffsetDateTime.now());
    this.setFullName("Anonymous");
    this.setPhone("000-000-0000");
    this.setDocument("000.000.0000");
    this.setEmail(UUID.randomUUID() + "@anonymous.com");
    this.setBirthDate(null);

  }

  public void enablePromotionNotifications() {
    this.setPromotionNotificationAllowed(true);

  }

  public void disablePromotionNotifications() {
    this.setPromotionNotificationAllowed(false);

  }

  public void changeName(String fullName) {
    this.setFullName(fullName);
  }

  public void changeEmail(String email) {
    this.setEmail(email);
  }

  public void changePhone(String phone) {
    this.setPhone(phone);
  }


  public UUID id() {
    return id;
  }

  public String fullName() {
    return fullName;
  }

  public LocalDate birthDate() {
    return birthDate;
  }

  public String email() {
    return email;
  }

  public String phone() {
    return phone;
  }

  public String document() {
    return document;
  }

  public Boolean isPromotionNotificationAllowed() {
    return promotionNotificationAllowed;
  }

  public Boolean isArchived() {
    return archived;
  }

  public OffsetDateTime registeredAt() {
    return registeredAt;
  }

  public OffsetDateTime archivedAt() {
    return archivedAt;
  }

  public Integer loyaltyPoints() {
    return loyaltyPoints;
  }

  private void setId(UUID id) {
    Objects.requireNonNull(id);
    this.id = id;
  }

  private void setFullName(String fullName) {
    Objects.requireNonNull(fullName, VALIDATION_ERROR_FULLNAME_IS_NULL);
    if (fullName.isBlank()) {
      throw new IllegalArgumentException(VALIDATION_ERROR_FULLNAME_IS_BLANK);
    }
    this.fullName = fullName;
  }

  private void setBirthDate(LocalDate birthDate) {
//    if (birthDate == null) {
//      this.birthDate = null;
//    }
//    if (birthDate.isAfter(LocalDate.now())) {
//      throw new IllegalArgumentException(VALIDATION_ERROR_BIRTHDATE_MUST_IN_PAST);
//    }

    this.birthDate = birthDate;
  }

  private void setEmail(String email) {
    FieldValidations.requiresEmail(email, VALIDATION_ERROR_EMAIL_IS_INVALID);
       this.email = email;
  }

  private void setPhone(String phone) {
    Objects.requireNonNull(phone);
    this.phone = phone;
  }

  private void setDocument(String document) {
    Objects.requireNonNull(document);
    this.document = document;
  }

  private void setPromotionNotificationAllowed(Boolean promotionNotificationAllowed) {
    Objects.requireNonNull(promotionNotificationAllowed);
    this.promotionNotificationAllowed = promotionNotificationAllowed;
  }

  private void setArchived(Boolean archived) {
    Objects.requireNonNull(archived);
    this.archived = archived;
  }

  private void setRegisteredAt(OffsetDateTime registeredAt) {
    Objects.requireNonNull(registeredAt);
    this.registeredAt = registeredAt;
  }

  private void setArchivedAt(OffsetDateTime archivedAt) {
    this.archivedAt = archivedAt;
  }

  private void setLoyaltyPoints(Integer loyaltyPoints) {
    Objects.requireNonNull(loyaltyPoints);
    this.loyaltyPoints = loyaltyPoints;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Customer customer = (Customer) o;
    return id.equals(customer.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
