package com.github.ppzarebski.qa.performance.tests.echowebsocket

import com.github.ppzarebski.qa.commons.helper.RandomGenerator
import com.github.ppzarebski.qa.performance.tests.BaseHttpProtocol
import com.github.ppzarebski.qa.websocket.config.ApiConfig.WS_HOST
import io.gatling.core.Predef._
import io.gatling.http.Predef._

//TODO: websocket connection to be fixed
class SendMessageSimulation extends Simulation {

  val wsProtocol = BaseHttpProtocol.wsProtocol(WS_HOST)
  var wsName: String = null

  val sendMessageSimulation = scenario("Connect to websocket and send message")
    .exec(
      ws("connect ws and send message").wsName(getWsName().toString())
        .connect("/")
        .onConnected(
          exec(ws("send message").wsName(wsName).sendText(RandomGenerator.faker.chuckNorris().fact()))
        ))
  setUp(sendMessageSimulation.inject(atOnceUsers(1))).protocols(wsProtocol)

  def getWsName(): Unit = {
    wsName = RandomGenerator.faker.pokemon().name() + RandomGenerator.getRandomInt(10, 999).toString
  }
}
