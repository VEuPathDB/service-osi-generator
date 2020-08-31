package org.veupathdb.service.osi.model.db;

import java.time.OffsetDateTime;

import org.veupathdb.service.osi.util.Errors;
import org.veupathdb.service.osi.util.Format;
import org.veupathdb.service.osi.util.Validation;

public class IdSetCollection
{
  private final long collectionId;

  private final String name;

  private final OffsetDateTime createdOn;

  private final long createdBy;

  public IdSetCollection(
    final long collectionId,
    final String name,
    final OffsetDateTime createdOn,
    final long createdBy
  ) {
    this.collectionId = Validation.oneMinimum(collectionId);
    this.name         = Validation.nonEmpty(name);
    this.createdOn    = Validation.nonNull(createdOn);
    this.createdBy    = Validation.oneMinimum(createdBy);
  }

  public long getId() {
    return collectionId;
  }

  public String getName() {
    return name;
  }

  public long getCreatedBy() {
    return createdBy;
  }

  public OffsetDateTime getCreatedOn() {
    return createdOn;
  }

  @Override
  public String toString() {
    return Errors.toRuntime(this, Format.Json()::writeValueAsString);
  }
}
