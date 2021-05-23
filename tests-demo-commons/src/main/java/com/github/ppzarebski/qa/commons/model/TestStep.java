package com.github.ppzarebski.qa.commons.model;

public enum TestStep {
  GIVEN("given"),
  WHEN("when"),
  THEN("then"),
  AND("and");

  private final String description;

  TestStep(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
}