package com.github.ppzarebski.qa.commons.tests.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.ppzarebski.qa.commons.json.JsonParser;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JsonParserIT {

  private static class Animal {
    public int id;
    public String name;

    public Animal() {
    }

    public Animal(int id, String name) {
      this.id = id;
      this.name = name;
    }
  }

  @Test
  void shouldReturnMapper() {
    assertThat(JsonParser.mapper).isNotNull();
  }

  @Test
  void shouldReturnSameMapperInstance() {
    var mapper1 = JsonParser.mapper;
    var mapper2 = JsonParser.mapper;
    assertThat(mapper1).isSameInstanceAs(mapper2);
  }

  @Test
  void shouldReturnSoftMapper() {
    assertThat(JsonParser.softMapper).isNotNull();
  }

  @Test
  void shouldReturnSameSoftMapperInstance() {
    var mapper1 = JsonParser.softMapper;
    var mapper2 = JsonParser.softMapper;
    assertThat(mapper1).isSameInstanceAs(mapper2);
  }

  @Test
  void shouldParseJsonString() {
    var animal = "{\"id\": 0, \"name\": \"Lion\"}";
    var animalObject = JsonParser.asTypeReference(new TypeReference<Animal>() {
    }, animal);
    assertThat(animalObject).isNotNull();
    assertThat(animalObject.id).isEqualTo(0);
    assertThat(animalObject.name).isEqualTo("Lion");
  }

  @Test
  void shouldConvertObjectToJson() {
    var animal = new Animal(1, "Tiger");
    var animalJson = JsonParser.toJson(animal);
    assertThat(animalJson).isNotNull();
    System.out.println(animalJson);
  }

  @Test
  void shouldFailOnUnrecognizedField() {
    var animal = "{\"id\": 0, \"name\": \"Lion\", \"unknown\": \"unknown\"}";
    assertThrows(RuntimeException.class, () -> JsonParser.asTypeReference(new TypeReference<Animal>() {
    }, animal), String.format("Cannot parse body to %s", Animal.class));
  }

  @Test
  void shouldNotFailOnUnrecognizedField() {
    var animal = "{\"id\": 0, \"name\": \"Lion\", \"unknown\": \"unknown\"}";
    var animalObject = JsonParser.asTypeReferenceWithIgnoreUnknown(new TypeReference<Animal>() {
    }, animal);
    assertThat(animalObject).isNotNull();
    assertThat(animalObject.id).isEqualTo(0);
    assertThat(animalObject.name).isEqualTo("Lion");
    assertThat(Arrays.stream(animalObject.getClass().getDeclaredFields()).noneMatch(e -> e.toString().equals("unknown"))).isTrue();
  }
}
