package org.veupathdb.service.osi.model.db;

import java.time.OffsetDateTime;
import java.util.Map;

import org.veupathdb.service.osi.model.db.raw.IdSetCollectionRow;
import org.veupathdb.service.osi.util.Validation;

public class IdSetCollection extends NewIdSetCollection
{
  private final int collectionId;
  private final OffsetDateTime createdOn;

  public IdSetCollection(
    int collectionId,
    String name,
    User createdBy,
    OffsetDateTime createdOn
  ) {
    super(name, createdBy);
    this.collectionId = Validation.oneMinimum(collectionId);
    this.createdOn    = Validation.nonNull(createdOn);
  }

  public IdSetCollection(
    int collectionId,
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
      row.getCollectionId(),
      row.getName(),
      users.get(row.getCreatedBy()),
      row.getCreatedOn()
    );
  }

  public int getCollectionId() {
    return collectionId;
  }

  public OffsetDateTime getCreatedOn() {
    return createdOn;
  }
}