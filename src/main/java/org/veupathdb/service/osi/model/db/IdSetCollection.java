package org.veupathdb.service.osi.model.db;

import java.time.OffsetDateTime;
import java.util.Map;

import org.veupathdb.service.osi.model.db.raw.IdSetCollectionRow;
import org.veupathdb.service.osi.util.Validation;

public class IdSetCollection extends NewIdSetCollection
{
  private final long collectionId;
  private final OffsetDateTime createdOn;

  public IdSetCollection(
    long collectionId,
    String name,
    User createdBy,
    OffsetDateTime createdOn
  ) {
    super(name, createdBy);
    this.collectionId = Validation.oneMinimum(collectionId);
    this.createdOn    = Validation.nonNull(createdOn);
  }

  public IdSetCollection(
    long collectionId,
    OffsetDateTime createdOn,
    NewIdSetCollection from
  ) {
    this(collectionId, from.getName(), from.getCreatedBy(), createdOn);
  }

  public IdSetCollection(
    IdSetCollectionRow row,
    Map < Integer, User > users
  ) {
    this(
      row.getId(),
      row.getName(),
      users.get(row.getCreatedBy()),
      row.getCreatedOn()
    );
  }

  public long getCollectionId() {
    return collectionId;
  }

  public OffsetDateTime getCreatedOn() {
    return createdOn;
  }
}
