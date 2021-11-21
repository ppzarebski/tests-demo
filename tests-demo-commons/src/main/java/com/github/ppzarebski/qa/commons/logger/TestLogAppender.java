package com.github.ppzarebski.qa.commons.logger;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.OutputStreamAppender;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayOutputStream;
import java.time.Instant;

public class TestLogAppender extends OutputStreamAppender<ILoggingEvent> {

  Level acceptedLevel = Level.INFO;
  LogHolder logHolder = LogHolder.getInstance();
  ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

  public TestLogAppender() {
    this.setEncoder(getDefaultEncoder());
    this.setOutputStream(outputStream);
    this.setImmediateFlush(false);
    this.start();
  }

  @Override
  public void append(ILoggingEvent event) {
    var logger = event.getLoggerName();
    var level = event.getLevel();
    if (logger.contains("RequestLogFilter") || logger.contains("ResponseLogFilter")) {
      logHolder.putEvent(StringUtils.substring(formatMessage(event), 0,
            StringUtils.ordinalIndexOf(formatMessage(event), "\n", 2)).concat("\r\n"));
    } else if (level.isGreaterOrEqual(acceptedLevel) && !logger.contains("TestCaseFilter")
          && !logger.contains("BaseSuite")) {
      logHolder.putEvent(formatMessage(event));
    }
  }

  private PatternLayoutEncoder getDefaultEncoder() {
    var encoder = new PatternLayoutEncoder();
    encoder.setContext(new LoggerContext());
    encoder.setPattern("%d{HH:mm:ss.SSS} %-5level %c{1} - %msg%n");
    encoder.start();
    return encoder;
  }

  private static String formatMessage(ILoggingEvent event) {
    return String.format("%s %s %s - %s", Instant.ofEpochMilli(event.getTimeStamp()), event.getLevel(),
          shortenLoggerName(event.getLoggerName()), event.getFormattedMessage());
  }

  private static String shortenLoggerName(String loggerName) {
    return loggerName.replaceAll("\\B\\w+(\\.[a-z])", "$1");
  }
}
