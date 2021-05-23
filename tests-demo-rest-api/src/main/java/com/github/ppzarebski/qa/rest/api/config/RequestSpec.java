package com.github.ppzarebski.qa.rest.api.config;

import com.github.ppzarebski.qa.commons.config.BaseRestSpecBuilder;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

import static com.github.ppzarebski.qa.rest.api.config.ApiConfig.PETSTORE_HOST;

public class RequestSpec {

  public static RequestSpecification PET_STORE_REQUEST_SPEC = getRequestSpec(PETSTORE_HOST);

  private static RequestSpecification getRequestSpec(String appUri) {
    return RestAssured.requestSpecification = BaseRestSpecBuilder.baseSpecBuilder()
          .setBaseUri(appUri)
          .build();
  }
}
