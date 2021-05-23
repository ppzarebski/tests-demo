package com.github.ppzarebski.qa.commons.tests.helper;

import com.github.javafaker.Faker;
import com.github.ppzarebski.qa.commons.helper.RandomGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RandomGeneratorIT {

  @Test
  void shouldReturnFaker() {
    assertThat(RandomGenerator.faker.getClass()).isEqualTo(Faker.class);
  }

  @Test
  void shouldReturnFakerSameInstance() {
    var faker1 = RandomGenerator.faker;
    var faker2 = RandomGenerator.faker;
    assertThat(faker1).isSameInstanceAs(faker2);
  }

  @ParameterizedTest
  @ValueSource(ints = {-122, -12, -1, 0, 1, 12, 12222})
  void returnRandomInt(int to) {
    var from = to - 123;
    var randomInt = RandomGenerator.getRandomInt(from, to);
    System.out.println(randomInt);
    assertThat(randomInt).isAtLeast(from);
    assertThat(randomInt).isAtMost(to);
  }

  @Test
  void returnExactValue() {
    var exactVal = RandomGenerator.getRandomInt(2, 2);
    System.out.println(exactVal);
    assertThat(exactVal).isEqualTo(2);
  }

  @ParameterizedTest
  @ValueSource(ints = {2, 0, -3})
  void throwException(int to) {
    var from = to + 1;
    System.out.println(assertThrows(RuntimeException.class, () -> RandomGenerator.getRandomInt(from, to)).getMessage());
  }
}
