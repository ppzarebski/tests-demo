package com.github.ppzarebski.qa.performance.tests.echowebsocket

import com.github.ppzarebski.qa.commons.helper.RandomGenerator
import com.github.ppzarebski.qa.performance.tests.BaseHttpProtocol
import com.github.ppzarebski.qa.websocket.config.ApiConfig.WS_HOST
import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.check.ws.WsTextFrameCheck
import io.gatling.http.protocol.HttpProtocolBuilder

//TODO: websocket message sending to be fixed
class SendMessageSimulation extends Simulation {

  val wsProtocol: HttpProtocolBuilder = BaseHttpProtocol.wsProtocol(WS_HOST)
  var message: String = getMessage().toString
  val conditionCheck: WsTextFrameCheck = ws.checkTextMessage("message check").check(regex(message))

  val sendMessageSimulation: ScenarioBuilder = scenario("Connect to websocket and send message")
    .exec(ws("ws")
      .connect("/")
      .onConnected(
        exec(ws("ws").sendText(message).await(5)(conditionCheck))
      ))
  setUp(sendMessageSimulation.inject(atOnceUsers(1))).protocols(wsProtocol)

  def getMessage(): Unit = {
    message = RandomGenerator.faker.chuckNorris().fact()
  }
}
