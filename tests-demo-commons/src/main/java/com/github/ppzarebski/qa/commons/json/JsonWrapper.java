package com.github.ppzarebski.qa.commons.json;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.spi.json.JacksonJsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;

public class JsonWrapper {

  private final DocumentContext context;
  private static final Configuration configuration = new Configuration.ConfigurationBuilder()
        .mappingProvider(new JacksonMappingProvider(JsonParser.mapper))
        .jsonProvider(new JacksonJsonProvider(JsonParser.mapper))
        .build();

  public JsonWrapper(Object object) {
    this.context = JsonPath.parse(JsonParser.toJson(object), configuration);
  }

  public JsonWrapper setField(String path, Object value) {
    context.set(path, value);
    return this;
  }

  public String asJsonString() {
    return context.jsonString();
  }
}
