package org.veupathdb.service.osi.model.db.raw;

import java.time.OffsetDateTime;

import org.veupathdb.service.osi.util.Validation;

public class IdSetCollectionRow
{
  private final long collectionId;
  private final String name;
  private final long createdBy;
  private final OffsetDateTime createdOn;

  public IdSetCollectionRow(
    long collectionId,
    String name,
    long createdBy,
    OffsetDateTime createdOn
  ) {
    this.collectionId = collectionId;
    this.name         = name;
    this.createdBy    = createdBy;
    this.createdOn    = createdOn;
  }

  public long getCollectionId() {
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
}
