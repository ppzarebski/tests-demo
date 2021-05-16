package com.github.ppzarebski.qa.commons.logger;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestCaseFilter implements BeforeEachCallback {

  private static final Logger log = LoggerFactory.getLogger(TestCaseFilter.class);

  @Override
  public void beforeEach(ExtensionContext context) {
    log.info(formatTestName(context));
  }

  private static String formatTestName(ExtensionContext context) {
    return String.format("%s / %s", context.getTestClass().orElseThrow().getPackageName(), getTestName(context));
  }

  private static String getTestName(ExtensionContext context) {
    var tcName = context.getTestMethod().orElseThrow().getName();
    if (context.getDisplayName() != null) {
      tcName = tcName.concat(String.format(": %s", context.getDisplayName()));
    }
    return tcName;
  }
}
