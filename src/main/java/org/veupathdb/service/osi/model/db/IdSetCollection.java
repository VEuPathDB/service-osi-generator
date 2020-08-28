package org.veupathdb.service.osi.model.db;

import java.time.OffsetDateTime;

public class IdSetCollection
{
  private final long collectionId;
  private final String name;
  private final long createdBy;
  private final OffsetDateTime createdOn;

  public IdSetCollection(
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
}
