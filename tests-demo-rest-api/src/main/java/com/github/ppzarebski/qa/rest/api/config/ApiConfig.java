package com.github.ppzarebski.qa.rest.api.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class ApiConfig {

  private static final Config CONFIG = ConfigFactory.load("config.conf");
  private static final Config API_CONFIG = CONFIG.getConfig("api");

  public static final String PETSTORE_HOST = API_CONFIG.getString("petstore.baseUrl");
}
