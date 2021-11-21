package com.github.ppzarebski.qa.commons.logger;

import java.util.ArrayList;
import java.util.List;

public class LogHolder {

  private final List<String> logs = new ArrayList<>();
  private static LogHolder INSTANCE = null;

  private LogHolder() {
  }

  public static LogHolder getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new LogHolder();
    }
    return INSTANCE;
  }

  public void putEvent(String message) {
    logs.add(message);
  }

  public List<String> getLogsAndClear() {
    var stepLogs = new ArrayList<>(logs);
    clearLogs();
    return stepLogs;
  }

  public List<String> getLogs() {
    return logs;
  }

  public void clearLogs() {
    logs.clear();
  }
}
