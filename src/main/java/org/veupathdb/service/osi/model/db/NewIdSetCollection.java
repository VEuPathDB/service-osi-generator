package org.veupathdb.service.osi.model.db;

import org.veupathdb.service.osi.util.Validation;

public class NewIdSetCollection
{
  private final String name;
  private final User createdBy;

  public NewIdSetCollection(String name, User createdBy) {
    this.name      = Validation.nonEmpty(name);
    this.createdBy = Validation.nonNull(createdBy);
  }

  public String getName() {
    return name;
  }

  public User getCreatedBy() {
    return createdBy;
  }
}
