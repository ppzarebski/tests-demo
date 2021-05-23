package com.github.ppzarebski.qa.websocket.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class EchoWebSocketClient extends BaseWebSocketClient {

  private static final Logger log = LoggerFactory.getLogger(EchoWebSocketClient.class);

  private final List<String> messages = new ArrayList<>();

  private EchoWebSocketClient(URI serverUri) {
    super(serverUri);
  }

  public static EchoWebSocketClient getInstance() {
    return new EchoWebSocketClient(URI.create(ApiConfig.WS_HOST));
  }

  @Override
  public void onMessage(String message) {
    log.info("Message received: {}", message);
    messages.add(message);
  }

  public void clearMessages() {
    log.info("Removing messages...");
    this.messages.clear();
  }

  public List<String> getMessages() {
    return this.messages;
  }

  public void sendMessage(String message) {
    log.info("Sending: {}", message);
    this.send(message);
  }
}
