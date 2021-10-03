package com.github.ppzarebski.qa.websocket.tests;

import com.github.ppzarebski.qa.commons.helper.RandomGenerator;
import com.github.ppzarebski.qa.commons.test.BaseSuite;
import com.github.ppzarebski.qa.websocket.config.EchoWebSocketClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.concurrent.TimeUnit;

import static com.google.common.truth.Truth.assertThat;
import static org.awaitility.Awaitility.await;

@DisplayName("websocket.org test")
public class EchoWebSocketTest extends BaseSuite {

  private static EchoWebSocketClient websocket;

  @AfterEach
  void afterEach() {
    if (websocket != null) {
      websocket.closeConnection();
    }
  }

  @DisplayName("should send message and receive the same in response")
  @Test
  void sendMessageTest() {
    _given("opened connection to web socket");
    websocket = EchoWebSocketClient.getInstance();
    websocket.openConnection();
    waitForWelcomeMessage();
    websocket.clearMessages();

    _when("message is sent");
    var message = RandomGenerator.faker.chuckNorris().fact();
    websocket.sendMessage(message);

    _then("the same message should be returned");
    await().pollInterval(100, TimeUnit.MILLISECONDS).timeout(5, TimeUnit.SECONDS).untilAsserted(() -> {
      var messages = websocket.getMessages();
      assertThat(messages).isNotEmpty();
      assertThat(messages.get(0)).isEqualTo(message);
    });
  }

  @DisplayName("should send message and receive the same in response multiple times on a single connection")
  @ParameterizedTest(name = "should send message and receive the same in response {0} times on single connection")
  @ValueSource(ints = {3, 5, 7, 11})
  void sendMessagesTest(int times) {
    _given("opened connection to web socket");
    websocket = EchoWebSocketClient.getInstance();
    websocket.openConnection();
    waitForWelcomeMessage();
    websocket.clearMessages();

    for (var i = times; i > 0; i--) {
      _when("message is sent");
      var message = RandomGenerator.faker.chuckNorris().fact();
      websocket.sendMessage(message);

      _then("the same message should be returned");
      await().pollInterval(100, TimeUnit.MILLISECONDS).timeout(5, TimeUnit.SECONDS).untilAsserted(() -> {
        var messages = websocket.getMessages();
        assertThat(messages).isNotEmpty();
        assertThat(messages.stream().anyMatch(e -> e.equals(message))).isTrue();
      });
    }
  }

  private static void waitForWelcomeMessage() {
    await().pollInterval(100, TimeUnit.MILLISECONDS).timeout(5, TimeUnit.SECONDS).untilAsserted(() -> {
      var messages = websocket.getMessages();
      assertThat(messages).isNotEmpty();
      assertThat(messages.get(0)).contains("Request served");
    });
  }
}
