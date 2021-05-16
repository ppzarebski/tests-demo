package com.github.ppzarebski.qa.commons.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ppzarebski.qa.commons.logger.RequestLogFilter;
import com.github.ppzarebski.qa.commons.logger.ResponseLogFilter;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SSLConfig;

public class BaseRestSpecBuilder {

  private static final RestAssuredConfig defaultRestAssuredConfig = getDefaultRestAssuredConfig();
  private static final RequestLogFilter requestLogFilter = new RequestLogFilter();
  private static final ResponseLogFilter responseLogFilter = new ResponseLogFilter();

  private static RestAssuredConfig getDefaultRestAssuredConfig() {
    return RestAssuredConfig.newConfig()
          .sslConfig(SSLConfig.sslConfig().relaxedHTTPSValidation())
          .httpClient(HttpClientConfig.httpClientConfig()
                .setParam("http.connection.timeout", 10000)
                .setParam("http.socket.timeout", 10000))
          .objectMapperConfig(ObjectMapperConfig.objectMapperConfig().jackson2ObjectMapperFactory((k, v) -> new ObjectMapper()));
  }

  public static RequestSpecBuilder baseSpecBuilder() {
    return baseSpecBuilder(defaultRestAssuredConfig);
  }

  public static RequestSpecBuilder baseSpecBuilder(RestAssuredConfig restAssuredConfig) {
    return new RequestSpecBuilder()
          .setContentType("application/json; charset=utf-8")
          .setAccept("application/json")
          .addFilter(requestLogFilter)
          .addFilter(responseLogFilter)
          .setConfig(restAssuredConfig);
  }
}
