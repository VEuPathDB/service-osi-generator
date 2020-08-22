package org.veupathdb.service.osi.model.db.raw;

import java.time.OffsetDateTime;

import org.veupathdb.service.osi.util.Validation;

public class IdSetCollectionRow
{
  private final int collectionId;
  private final String name;
  private final int createdBy;
  private final OffsetDateTime createdOn;

  public IdSetCollectionRow(
    int collectionId,
    String name,
    int createdBy,
    OffsetDateTime createdOn
  ) {
    this.collectionId = collectionId;
    this.name         = name;
    this.createdBy    = createdBy;
    this.createdOn    = createdOn;
  }

  public int getCollectionId() {
    return collectionId;
  }

  public String getName() {
    return name;
  }

  public int getCreatedBy() {
    return createdBy;
  }

  public OffsetDateTime getCreatedOn() {
    return createdOn;
  }
}
