package com.github.ppzarebski.qa.rest.api.tests.petstore;

import com.github.ppzarebski.qa.commons.helper.RandomGenerator;
import com.github.ppzarebski.qa.commons.test.BaseSuite;
import com.github.ppzarebski.qa.rest.api.services.petstore.PetStoreApiService;
import com.github.ppzarebski.qa.rest.api.services.petstore.model.PetDefaultResponseBody;
import com.github.ppzarebski.qa.rest.api.services.petstore.model.PetStatusParams;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.github.ppzarebski.qa.rest.api.services.petstore.model.PetStatus.AVAILABLE;
import static com.google.common.truth.Truth.assertThat;

@DisplayName("PetStoreApi - pet controller: find pet by id")
public class PetFindByIdTest extends BaseSuite {

  @DisplayName("should return pet by id")
  @Test
  void getPetByIdTest() {
    _given("existing pet id");
    var petId = PetStoreApiService.findPetsByStatus(PetStatusParams.of(AVAILABLE)).get(0).id;

    _when("pet by id is requested");
    var pet = PetStoreApiService.findPetById(petId);

    _then("pet should be returned");
    assertThat(pet).isNotNull();
    assertThat(pet.id).isEqualTo(petId);
    assertThat(pet.name).isNotNull();
    assertThat(pet.status).isNotNull();
    assertThat(pet.photoUrls).isNotEmpty();
    assertThat(pet.category.id).isNotNull();
    assertThat(pet.category.name).isNotNull();
    assertThat(pet.tags).isNotEmpty();
  }

  @DisplayName("should return error for not existing pet id")
  @Test
  void notExistingPetIdTest() {
    _given("not existing pet id");
    var petId = -1;

    _when("pet by not existing id is requested");
    var response = PetStoreApiService.Raw.findPetById(petId);

    _then("error should be returned");
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_NOT_FOUND);
    var error = response.as(PetDefaultResponseBody.class);
    assertThat(error.message).isEqualTo("Pet not found");
  }

  @DisplayName("should return error for invalid pet id")
  @Test
  void invalidPetIdTest() {
    _given("invalid pet id");
    var petId = RandomGenerator.faker.pokemon().name();

    _when("pet by invalid id is requested");
    var response = PetStoreApiService.Raw.findPetById(petId);

    _then("error should be returned");
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_NOT_FOUND);
    var error = response.as(PetDefaultResponseBody.class);
    assertThat(error.message).contains("NumberFormatException");
  }
}
