package com.github.ppzarebski.qa.rest.api.tests.petstore;

import com.github.ppzarebski.qa.commons.helper.RandomGenerator;
import com.github.ppzarebski.qa.commons.test.BaseSuite;
import com.github.ppzarebski.qa.rest.api.services.petstore.PetStoreApiService;
import com.github.ppzarebski.qa.rest.api.services.petstore.model.PetStatus;
import com.github.ppzarebski.qa.rest.api.services.petstore.model.PetStatusParams;
import io.qameta.allure.Issue;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.params.provider.Arguments.of;

@DisplayName("PetStoreApi - pet controller: find pet by status")
public class PetFindByStatusTest extends BaseSuite {

  @DisplayName("should return pets by status(es)")
  @ParameterizedTest(name = "should return pets by status param: {0}")
  @MethodSource("provideValidInputs")
  void findPetsByStatus(List<PetStatus> params) {
    _given("pet status params");
    var queryParams = PetStatusParams.of(params);

    _when("pets by status are requested");
    var pets = PetStoreApiService.findPetsByStatus(queryParams);

    _then("pets by status should be returned");
    if (!pets.isEmpty()) {
      assertThat(pets.stream().allMatch(e -> params.contains(e.status))).isTrue();
    }
  }

  private static Stream<Arguments> provideValidInputs() {
    return Stream.of(
          of(List.of(PetStatus.AVAILABLE)),
          of(List.of(PetStatus.PENDING)),
          of(List.of(PetStatus.SOLD)),
          of(List.of(PetStatus.AVAILABLE, PetStatus.SOLD)),
          of(List.of(PetStatus.PENDING, PetStatus.SOLD)),
          of(List.of(PetStatus.AVAILABLE, PetStatus.PENDING)),
          of(List.of(PetStatus.AVAILABLE, PetStatus.PENDING, PetStatus.SOLD))
    );
  }

  @Issue("SomeIssue")
  @DisplayName("should return error for not exiting status")
  @Test
  void notExistingStatusTest() {
    _given("not existing pet status");
    var status = RandomGenerator.faker.pokemon().name();
    var params = PetStatusParams.empty().asMap();
    params.put("status", status);

    _when("pets by status are requested with not existing status");
    var response = PetStoreApiService.Raw.findPetsByStatus(params);

    _then("empty list should be returned");
    assertThat(response.getStatusCode()).isIn(List.of(HttpStatus.SC_NOT_FOUND, HttpStatus.SC_BAD_REQUEST));
  }

  @Issue("SomeIssue")
  @DisplayName("should return error for missing required status param")
  @Test
  void missingParamTest() {
    _given("missing param");
    var params = PetStatusParams.empty().asMap();
    params.clear();

    _when("pets by status are requested with missing required param");
    var response = PetStoreApiService.Raw.findPetsByStatus(params);

    _then("error should be returned");
    assertThat(response.getStatusCode()).isIn(List.of(HttpStatus.SC_NOT_FOUND, HttpStatus.SC_BAD_REQUEST));
  }
}
