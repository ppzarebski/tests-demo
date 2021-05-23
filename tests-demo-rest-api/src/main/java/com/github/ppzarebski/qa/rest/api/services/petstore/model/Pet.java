package com.github.ppzarebski.qa.rest.api.services.petstore.model;

import com.github.ppzarebski.qa.commons.model.RestEntity;

import java.util.List;

public class Pet extends RestEntity {

  public Long id;
  public Tag category;
  public String name;
  public List<String> photoUrls;
  public List<Tag> tags;
  public PetStatus status;

  public static class Tag {
    public Long id;
    public String name;
  }
}
