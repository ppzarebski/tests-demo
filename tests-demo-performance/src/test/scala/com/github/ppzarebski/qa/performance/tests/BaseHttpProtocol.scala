package com.github.ppzarebski.qa.performance.tests

import io.gatling.core.Predef._
import io.gatling.http.Predef.http
import io.gatling.http.protocol.HttpProtocolBuilder

object BaseHttpProtocol {

  def httpProtocol(baseUri: String): HttpProtocolBuilder = {
    http.baseUrl(baseUri)
      .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
      .acceptEncodingHeader("gzip, deflate")
      .acceptLanguageHeader("en-US,en;q=0.5")
      .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")
  }

  def wsProtocol(baseUri: String) = {
    val uriWithoutProtocol = getUriWithoutProtocol(baseUri)
    http.baseUrl("http://" + uriWithoutProtocol)
      .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
      .doNotTrackHeader("1")
      .acceptLanguageHeader("en-US,en;q=0.5")
      .acceptEncodingHeader("gzip, deflate")
      .userAgentHeader("Gatling2")
      .wsBaseUrl("wss://" + uriWithoutProtocol)
  }

  def getUriWithoutProtocol(uri: String) = {
    uri.substring(uri.indexOf("://") + 3)
  }

}
