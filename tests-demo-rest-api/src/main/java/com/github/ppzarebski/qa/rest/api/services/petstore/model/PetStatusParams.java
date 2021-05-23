package com.github.ppzarebski.qa.rest.api.services.petstore.model;

import com.github.ppzarebski.qa.commons.model.QueryParamsEntity;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PetStatusParams extends QueryParamsEntity {

  public String status;

  public PetStatusParams() {
  }

  private PetStatusParams(List<PetStatus> statuses) {
    this.status = statuses.stream().map(PetStatus::toString).collect(Collectors.joining(","));
  }

  public static PetStatusParams empty() {
    return new PetStatusParams();
  }

  public static PetStatusParams of(PetStatus... statuses) {
    return of(Arrays.asList(statuses));
  }

  public static PetStatusParams of(List<PetStatus> statuses) {
    return new PetStatusParams(statuses);
  }
}
