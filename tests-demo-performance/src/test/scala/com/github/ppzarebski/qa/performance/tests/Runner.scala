package com.github.ppzarebski.qa.performance.tests

import com.github.ppzarebski.qa.performance.tests.petstore.FindPetByStatusSimulation

object Runner extends BaseRunner {

  runSimulation(classOf[FindPetByStatusSimulation].getName)

}
