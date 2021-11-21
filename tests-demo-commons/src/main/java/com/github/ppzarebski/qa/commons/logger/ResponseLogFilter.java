package com.github.ppzarebski.qa.commons.logger;

import io.qameta.allure.Attachment;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class ResponseLogFilter implements Filter {

  private static final Logger log = LoggerFactory.getLogger(ResponseLogFilter.class);
  private static final int MAX_RESP_LENGTH = 10000;

  @Override
  public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec,
                         FilterContext context) {
    var response = context.next(requestSpec, responseSpec);
    var formattedResponse = formatResponse(response);
    saveResponse(formattedResponse, String.valueOf(response.getStatusCode()));
    log.info("<<= Response received [{}ms]: \n{}", response.getTimeIn(TimeUnit.MILLISECONDS), shortenResponseLog(formattedResponse));
    return response;
  }

  private static String formatResponse(Response response) {
    var result = new StringBuilder();
    result.append(String.format("HTTP Status code: %s\r\n", response.getStatusCode()));
    response.getHeaders().forEach(e -> result.append(String.format("%s: %s\r\n", e.getName(), e.getValue())));
    result.append(formatBody(response));
    result.append("\r\n");
    return result.toString();
  }

  private static String shortenResponseLog(String response) {
    if (response.length() > MAX_RESP_LENGTH) {
      response = response.substring(0, MAX_RESP_LENGTH).concat("...body log too big. For entire body look at test report.");
    }
    return response;
  }

  private static String formatBody(Response response) {
    if (response.getContentType().equals("application/octet-stream") || response.getContentType().equals("application/pdf")) {
      return "Body is a binary, logging file content skipped.";
    } else {
      return response.getBody().asPrettyString();
    }
  }

  @Attachment(value = "Response: {statusCode}", type = "text/plain")
  private static String saveResponse(String responseLog, String statusCode) {
    return responseLog;
  }
}
