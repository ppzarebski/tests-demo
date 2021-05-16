package com.github.ppzarebski.qa.commons.logger;

import io.qameta.allure.Attachment;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestLogFilter implements Filter {

  private static final Logger log = LoggerFactory.getLogger(RequestLogFilter.class);

  @Override
  public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec,
                         FilterContext context) {
    var request = formatRequest(requestSpec);
    log.info("=>> Request sent:\n{}", request);
    saveRequest(request, requestSpec.getMethod(), requestSpec.getURI());
    return context.next(requestSpec, responseSpec);
  }

  private static String formatRequest(FilterableRequestSpecification requestSpec) {
    var request = new StringBuilder();
    request.append(String.format("Method: %s url: %s\n", requestSpec.getMethod(), requestSpec.getURI()));
    requestSpec.getHeaders().forEach(h -> request.append(String.format("%s: %s\n", h.getName(), h.getValue())));
    if (requestSpec.getBody() != null) {
      request.append(String.format("Body:\n%s\n", requestSpec.getBody().toString()));
    }
    if (requestSpec.getMultiPartParams() != null && !requestSpec.getMultiPartParams().isEmpty()) {
      request.append("Body:\n");
      requestSpec.getMultiPartParams().forEach(e -> request.append(String.format("Form-data: %s: %s\n", e.getControlName(), e.getContent().toString())));
    }
    if (requestSpec.getFormParams() != null && !requestSpec.getFormParams().isEmpty()) {
      request.append("Body:\n");
      requestSpec.getFormParams().forEach((k, v) -> request.append(String.format("Form-param: %s=%s\n", k, v)));
    }
    return request.toString();
  }

  @Attachment(value = "Request: {method} {url}", type = "text/plain")
  private static String saveRequest(String requestLog, String method, String url) {
    return requestLog;
  }
}
