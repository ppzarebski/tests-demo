package com.github.ppzarebski.qa.commons.test;

import com.github.ppzarebski.qa.commons.logger.TestCaseFilter;
import com.github.ppzarebski.qa.commons.model.TestStep;
import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StepResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.util.stream.Collectors;

@ExtendWith(TestCaseFilter.class)
public class BaseSuite {

  private static final Logger log = LoggerFactory.getLogger(BaseSuite.class);
  private String currentStepId;

  @BeforeEach
  void beforeEach() {
    Allure.getLifecycle().updateTestCase(tc -> {
      var issues = tc.getLinks().stream()
            .map(e -> String.format("[%s]", e.getName()))
            .collect(Collectors.joining());
      tc.setName(issues.concat(" " + tc.getName()));
    });
  }

  protected void _given(String infoLog, Object... params) {
    registerStep(TestStep.GIVEN, String.format(infoLog, params));
  }

  protected void _when(String infoLog, Object... params) {
    registerStep(TestStep.WHEN, String.format(infoLog, params));
  }

  protected void _then(String infoLog, Object... params) {
    registerStep(TestStep.THEN, String.format(infoLog, params));
  }

  protected void _and(String infoLog, Object... params) {
    registerStep(TestStep.AND, String.format(infoLog, params));
  }

  private void registerStep(TestStep step, String infoLog) {
    var stepLog = String.format("%s: %s", step.getDescription(), infoLog);
    if (currentStepId != null) {
      Allure.getLifecycle().stopStep(currentStepId);
    }
    currentStepId = UUID.randomUUID().toString();
    Allure.getLifecycle()
          .startStep(currentStepId, new StepResult().setName(stepLog).setDescription(stepLog).setStatus(Status.PASSED));
    log.info(stepLog);
  }
}
