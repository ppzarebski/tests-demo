package com.github.ppzarebski.qa.commons.helper;

import com.github.javafaker.Faker;

import java.util.Random;

public class RandomGenerator {

  public static final Faker faker = new Faker();

  public static Integer getRandomInt(int from, int to) {
    var random = new Random();
    if (from > to) {
      throw new RuntimeException("Invalid range provided");
    }
    return random.nextInt(to - from + 1) + from;
  }
}
