package com.github.ppzarebski.qa.commons.model;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class QueryParamsEntity {
  private static final ObjectMapper mapper = new ObjectMapper();

  public Map<String, Object> asMap() {
    return mapper.convertValue(this, new TypeReference<>() {
    });
  }
}
