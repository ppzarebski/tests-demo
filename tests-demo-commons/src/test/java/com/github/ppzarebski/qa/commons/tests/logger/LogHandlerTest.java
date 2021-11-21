package com.github.ppzarebski.qa.commons.tests.logger;

import com.github.ppzarebski.qa.commons.logger.LogHolder;
import com.github.ppzarebski.qa.commons.test.BaseSuite;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogHandlerTest extends BaseSuite {

  private static final Logger logger = LoggerFactory.getLogger(LogHandlerTest.class);
  private static final LogHolder logHolder = LogHolder.getInstance();

  @AfterEach
  void clearLogs() {
    logHolder.clearLogs();
  }

  @Test
  void shouldCatchLogs() {
    //given
    var infoMessage = "info - catch me if you can";
    var warnMessage = "warn - catch me if you can";
    var errorMessage = "error - catch me if you can";

    //when
    logger.info(infoMessage);
    logger.warn(warnMessage);
    logger.error(errorMessage);

    //then
    var logs = logHolder.getLogs();
    assert !logs.isEmpty();
    assert logs.size() == 3;
    assert logs.get(0).contains(infoMessage);
    assert logs.get(1).contains(warnMessage);
    assert logs.get(2).contains(errorMessage);
  }

  @Test
  void shouldIgnoreDebugLogs() {
    //when
    logger.debug("catch me if you can");

    //then
    var logs = logHolder.getLogs();
    assert logs.isEmpty();
  }
}
