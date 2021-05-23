package com.github.ppzarebski.qa.commons.helper;

import java.io.File;

public class Resources {

  public static File asFile(String resourcePath) {
    var resUrl = Resources.class.getClassLoader().getResource(resourcePath);
    if (resUrl == null) {
      throw new RuntimeException(String.format("Resource %s not found.", resourcePath));
    }
    return new File(resUrl.getFile());
  }
}
