package com.github.ppzarebski.qa.commons.helper;

import java.util.Arrays;

public class EnumHelper {

  public static <T extends Enum<?>> T getRandom(Class<T> tEnum) {
    if (tEnum.getEnumConstants().length == 0) {
      throw new RuntimeException(String.format("Enum: %s - has no values!", tEnum.getSimpleName()));
    }
    var enumVals = Arrays.asList(tEnum.getEnumConstants());
    return enumVals.get(RandomGenerator.getRandomInt(0, enumVals.size() - 1));
  }
}
