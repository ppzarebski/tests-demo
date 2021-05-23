package com.github.ppzarebski.qa.performance.tests

import java.nio.file.Path

import com.github.ppzarebski.qa.commons.helper.Resources
import io.gatling.app.Gatling
import io.gatling.core.config.GatlingPropertiesBuilder


class BaseRunner extends App {

  val gatlingConf: Path = Resources.asFile("gatling.conf").toPath
  val projectRootDir = gatlingConf.getParent.getParent

  val mavenSourcesDir = projectRootDir.resolve("scala")
  System.out.println(mavenSourcesDir)
  val mavenResourcesDir = projectRootDir.resolve("resources")
  val mavenTargetDir = projectRootDir.resolve("target")
  val mavenBinariesDir = mavenTargetDir.resolve("classes")

  val resultsDir = mavenTargetDir.resolve("gatling")

  def runSimulation(simulationClass: String): Unit = {
    val props = new GatlingPropertiesBuilder()
      .resourcesDirectory(mavenResourcesDir.toString)
      .resultsDirectory(resultsDir.toString)
      .binariesDirectory(mavenBinariesDir.toString)
      .simulationsDirectory(mavenTargetDir.toString)
      .simulationClass(simulationClass)
      .build

    Gatling.fromMap(props)
  }
}
