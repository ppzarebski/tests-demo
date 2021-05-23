package com.github.ppzarebski.qa.commons.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class JsonParser {

  private static final Logger log = LoggerFactory.getLogger(JsonParser.class);

  public static final ObjectMapper mapper;
  public static final ObjectMapper softMapper;


  private static final DeserializationProblemHandler unknownPropsHandler = new DeserializationProblemHandler() {
    @Override
    public boolean handleUnknownProperty(DeserializationContext ctxt, com.fasterxml.jackson.core.JsonParser jp,
                                         JsonDeserializer<?> deserializer, Object beanOrClass, String propertyName) {
      log.warn("Unrecognized property detected: '{}' at '{}'.", propertyName, beanOrClass.getClass().getSimpleName());
      return true;
    }
  };

  static {
    mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    mapper.configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true);

    softMapper = mapper.copy();
    softMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    softMapper.addHandler(unknownPropsHandler);
  }

  public static String toJson(Object object) {
    try {
      return mapper.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(String.format("Cannot create json from object: %s because of: %s", object.getClass().getSimpleName(), e.getMessage()), e);
    }
  }

  public static <T> T asTypeReference(TypeReference<T> typeReference, String json) {
    try {
      return mapper.readValue(json, typeReference);
    } catch (IOException e) {
      throw new RuntimeException(String.format("Cannot parse body to %s", typeReference.getClass()), e);
    }
  }

  public static <T> T asTypeReferenceWithIgnoreUnknown(TypeReference<T> typeReference, String json) {
    try {
      return softMapper.readValue(json, typeReference);
    } catch (IOException e) {
      throw new RuntimeException(String.format("Cannot parse body to %s", typeReference.getClass()), e);
    }
  }
}
