package com.github.ppzarebski.qa.performance.tests.petstore

import com.github.ppzarebski.qa.performance.tests.BaseHttpProtocol
import com.github.ppzarebski.qa.rest.api.config.ApiConfig.PETSTORE_HOST
import com.github.ppzarebski.qa.rest.api.services.petstore.PetStoreApiService.PETS_BY_STATUS
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class FindPetByStatusSimulation extends Simulation {

  val httpProtocol = BaseHttpProtocol.httpProtocol(PETSTORE_HOST)

  val findPetByStatusSimulation = scenario("Find pets by status")
    .exec(http("find pet by status available").get(PETS_BY_STATUS + "?status=available"))
    .exec(http("find pet by status available").get(PETS_BY_STATUS + "?status=sold"))
    .exec(http("find pet by status available").get(PETS_BY_STATUS + "?status=pending"))

  setUp(findPetByStatusSimulation.inject(atOnceUsers(220))).protocols(httpProtocol)

}
