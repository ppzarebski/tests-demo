package com.github.ppzarebski.qa.commons.model;

import com.github.ppzarebski.qa.commons.json.JsonWrapper;

import java.io.Serializable;

public abstract class RestEntity implements Serializable {

  public JsonWrapper toJsonWrapper() {
    return new JsonWrapper(this);
  }
}
