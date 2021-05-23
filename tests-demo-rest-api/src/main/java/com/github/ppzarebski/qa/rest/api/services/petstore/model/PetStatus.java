package com.github.ppzarebski.qa.rest.api.services.petstore.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum PetStatus {
  AVAILABLE, PENDING, SOLD;

  @JsonCreator
  public static PetStatus fromString(String key) {
    return key != null ? PetStatus.valueOf(key.toUpperCase()) : null;
  }

  @Override
  public String toString() {
    return super.toString().toLowerCase();
  }
}
