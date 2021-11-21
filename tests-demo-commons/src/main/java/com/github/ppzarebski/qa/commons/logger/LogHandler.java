package com.github.ppzarebski.qa.commons.logger;

import io.qameta.allure.Attachment;
import io.qameta.allure.listener.StepLifecycleListener;
import io.qameta.allure.model.StepResult;
import org.junit.jupiter.api.extension.Extension;

public class LogHandler implements Extension, StepLifecycleListener {

  LogHolder logHolder = LogHolder.getInstance();

  @Attachment(value = "logs_{currentStep}", type = "text/plain", fileExtension = "txt")
  private String getLogs(String currentStep) {
    return String.join("\r\n", logHolder.getLogsAndClear()).trim();
  }

  @Override
  public void beforeStepStop(StepResult result) {
    getLogs(result.getName());
  }
}
