package com.github.ppzarebski.qa.commons.tests.helper;

import com.github.ppzarebski.qa.commons.helper.EnumHelper;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EnumHelperIT {

  private enum Animal {
    DOG, CAT, RABBIT
  }

  private enum Empty {
  }

  @Test
  void shouldReturnRandomEnumValue() {
    var randomVal = EnumHelper.getRandom(Animal.class);
    System.out.println(randomVal);
    assertThat(randomVal).isIn(Arrays.asList(Animal.values()));
  }

  @Test
  void shouldThrowException() {
    System.out.println(assertThrows(RuntimeException.class, () -> EnumHelper.getRandom(Empty.class)).getMessage());
  }
}
