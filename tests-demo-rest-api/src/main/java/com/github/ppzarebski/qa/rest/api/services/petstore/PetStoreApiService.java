package com.github.ppzarebski.qa.rest.api.services.petstore;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.ppzarebski.qa.commons.model.ResponseWrapper;
import com.github.ppzarebski.qa.rest.api.services.petstore.model.Pet;
import com.github.ppzarebski.qa.rest.api.services.petstore.model.PetDefaultResponseBody;
import com.github.ppzarebski.qa.rest.api.services.petstore.model.PetStatus;
import com.github.ppzarebski.qa.rest.api.services.petstore.model.PetStatusParams;
import org.apache.http.HttpStatus;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.ppzarebski.qa.rest.api.config.RequestSpec.PET_STORE_REQUEST_SPEC;
import static io.restassured.RestAssured.given;

public class PetStoreApiService {

  private static final String PETS = "/pet";
  private static final String PET = "/pet/{id}";
  private static final String PET_IMAGE = "/pet/{id}/uploadImage";
  public static final String PETS_BY_STATUS = "/pet/findByStatus";

  public static Pet addPet(Pet petInput) {
    return Raw.addPet(petInput).expectStatusCode(HttpStatus.SC_OK).asTypeReference(new TypeReference<>() {
    });
  }

  public static PetDefaultResponseBody deletePet(Long id) {
    return Raw.deletePet(id).expectStatusCode(HttpStatus.SC_OK).asTypeReference(new TypeReference<>() {
    });
  }

  public static Pet updatePet(Pet petInput) {
    return Raw.updatePet(petInput).expectStatusCode(HttpStatus.SC_OK).asTypeReference(new TypeReference<>() {
    });
  }

  public static PetDefaultResponseBody updatePetPartially(Long id, String name, PetStatus status) {
    return Raw.updatePetPartially(id, name, status).expectStatusCode(HttpStatus.SC_OK).asTypeReference(new TypeReference<>() {
    });
  }

  public static Pet findPetById(Long id) {
    return Raw.findPetById(id).expectStatusCode(HttpStatus.SC_OK).asTypeReference(new TypeReference<>() {
    });
  }

  public static List<Pet> findPetsByStatus(PetStatusParams params) {
    return Raw.findPetsByStatus(params.asMap()).expectStatusCode(HttpStatus.SC_OK).asTypeReference(new TypeReference<>() {
    });
  }

  public static PetDefaultResponseBody uploadImage(Long id, File file) {
    return Raw.uploadImage(id, file).expectStatusCode(HttpStatus.SC_OK).asTypeReference(new TypeReference<>() {
    });
  }

  public static class Raw {

    @SuppressWarnings("unchecked")
    public static ResponseWrapper<Pet> addPet(Object petInput) {
      return ResponseWrapper.from(given(PET_STORE_REQUEST_SPEC)
            .body(petInput)
            .when()
            .post(PETS)
            .thenReturn());
    }

    @SuppressWarnings("unchecked")
    public static ResponseWrapper<PetDefaultResponseBody> deletePet(Object id) {
      return ResponseWrapper.from(given(PET_STORE_REQUEST_SPEC)
            .pathParam("id", id)
            .when()
            .delete(PET)
            .thenReturn());
    }

    @SuppressWarnings("unchecked")
    public static ResponseWrapper<Pet> updatePet(Object petInput) {
      return ResponseWrapper.from(given(PET_STORE_REQUEST_SPEC)
            .body(petInput)
            .when()
            .put(PETS)
            .thenReturn());
    }

    @SuppressWarnings("unchecked")
    public static ResponseWrapper<PetDefaultResponseBody> updatePetPartially(Object id, Object name, Object status) {
      var map = new HashMap<String, Object>();
      if (name != null) {
        map.put("name", name);
      }
      if (status != null) {
        map.put("status", status);
      }
      return ResponseWrapper.from(given(PET_STORE_REQUEST_SPEC)
            .pathParam("id", id)
            .contentType("application/x-www-form-urlencoded; charset=UTF-8")
            .formParams(map)
            .when()
            .post(PET)
            .thenReturn());
    }

    @SuppressWarnings("unchecked")
    public static ResponseWrapper<Pet> findPetById(Object id) {
      return ResponseWrapper.from(given(PET_STORE_REQUEST_SPEC)
            .pathParam("id", id)
            .when()
            .get(PET)
            .thenReturn());
    }

    @SuppressWarnings("unchecked")
    public static ResponseWrapper<List<Pet>> findPetsByStatus(Map<String, Object> params) {
      return ResponseWrapper.from(given(PET_STORE_REQUEST_SPEC)
            .queryParams(params)
            .when()
            .get(PETS_BY_STATUS)
            .thenReturn());
    }

    @SuppressWarnings("unchecked")
    public static ResponseWrapper<PetDefaultResponseBody> uploadImage(Object id, Object file) {
      return ResponseWrapper.from(given(PET_STORE_REQUEST_SPEC)
            .pathParam("id", id)
            .contentType("multipart/form-data")
            .multiPart("additionalMetadata", "file")
            .multiPart("file", file)
            .when()
            .post(PET_IMAGE)
            .thenReturn());
    }
  }
}
