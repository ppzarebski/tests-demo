package com.github.ppzarebski.qa.websocket.config;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.Arrays;
import java.util.stream.Collectors;

public abstract class BaseWebSocketClient extends WebSocketClient {

  private static final Logger log = LoggerFactory.getLogger(BaseWebSocketClient.class);

  public BaseWebSocketClient(URI serverUri) {
    super(serverUri);
  }

  @Override
  public void onOpen(ServerHandshake serverHandshake) {
    log.info("Connection opened. {} {}", serverHandshake.getHttpStatus(), serverHandshake.getHttpStatusMessage());
  }

  public abstract void onMessage(String s);

  @Override
  public void onClose(int i, String s, boolean b) {
    log.info("Connection closed.");
  }

  @Override
  public void onError(Exception e) {
    var stackTrace = Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.joining(""));
    log.error("Something went wrong... {}\n{}", e.getMessage(), stackTrace);
  }

  public void openConnection() {
    log.info("Opening connection to: {}", this.getURI());
    try {
      this.connectBlocking();
    } catch (InterruptedException e) {
      throw new RuntimeException(String.format("Something wen wrong while connecting: {}", e.getMessage()));
    }
  }

  public void closeConnection() {
    log.info("Closing connection to: {}", this.getURI());
    this.close();
  }
}
