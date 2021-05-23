package com.github.ppzarebski.qa.commons.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.ppzarebski.qa.commons.json.JsonParser;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.response.ResponseBodyExtractionOptions;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

import static com.google.common.truth.Truth.assertThat;

public class ResponseWrapper<T> {

  private Integer statusCode;
  private String statusLine;
  private Headers headers;
  private ResponseBodyExtractionOptions body;
  private Long time;

  private ResponseWrapper(Integer statusCode, String statusLine, Headers headers, ResponseBodyExtractionOptions body, Long time) {
    this.statusCode = statusCode;
    this.statusLine = statusLine;
    this.headers = headers;
    this.body = body;
    this.time = time;
  }

  public Integer getStatusCode() {
    return statusCode;
  }

  public String getStatusLine() {
    return statusLine;
  }

  public Headers getHeaders() {
    return headers;
  }

  public ResponseBodyExtractionOptions getBody() {
    return body;
  }

  public Long getTime() {
    return time;
  }

  public static ResponseWrapper from(Response response) {
    return new ResponseWrapper<>(
          response.getStatusCode(),
          response.getStatusLine(),
          new Headers(response.getHeaders().asList().stream().map(h -> new Header(h.getName(), h.getValue())).collect(Collectors.toList())),
          response.then().extract().body(),
          response.getTime());
  }

  public ResponseWrapper<T> expectStatusCode(Integer... status) {
    assertThat(this.getStatusCode()).isIn(Arrays.asList(status));
    return this;
  }

  public <T> T asTypeReference(TypeReference<T> typeReference) {
    return asTypeReference(typeReference, true);
  }

  public <T> T asTypeReferenceIgnoreUnknownProps(TypeReference<T> typeReference) {
    return asTypeReference(typeReference, false);
  }

  public <T> T asTypeReference(TypeReference<T> typeReference, boolean failOnUnknownProps) {
    var objectMapper = failOnUnknownProps ? JsonParser.mapper : JsonParser.softMapper;
    try {
      return objectMapper.readValue(this.body.asInputStream(), typeReference);
    } catch (IOException e) {
      throw new RuntimeException(String.format("Cannot parse body to %s", typeReference.getClass()), e);
    }
  }

  public <T> T as(Class<T> clazz) {
    return this.body.as(clazz);
  }
}
