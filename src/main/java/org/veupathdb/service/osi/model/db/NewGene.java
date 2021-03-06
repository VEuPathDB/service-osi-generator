package org.veupathdb.service.osi.model.db;

import org.veupathdb.service.osi.util.Errors;
import org.veupathdb.service.osi.util.Format;
import org.veupathdb.service.osi.util.Validation;

public class NewGene
{
  private final IdSet idSet;

  private final String identifier;

  private final User createdBy;

  public NewGene(
    final IdSet idSet,
    final String identifier,
    final User createdBy
  ) {
    this.idSet      = Validation.nonNull(idSet);
    this.identifier = Validation.nonEmpty(identifier);
    this.createdBy  = Validation.nonNull(createdBy);
  }

  public IdSet getIdSet() {
    return idSet;
  }

  public String getIdentifier() {
    return identifier;
  }

  public User getCreatedBy() {
    return createdBy;
  }

  @Override
  public String toString() {
    return Errors.toRuntime(this, Format.Json()::writeValueAsString);
  }
}
