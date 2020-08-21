package org.veupathdb.service.osi.model.db;

import java.time.OffsetDateTime;

import org.veupathdb.service.osi.util.Validation;

public class IdSet extends NewIdSet
{
  private final int idSetId;

  private final OffsetDateTime createdOn;

  public IdSet(
    int idSetId,
    IdSetCollection collection,
    Organism organism,
    String template,
    User createdBy,
    OffsetDateTime createdOn
  ) {
    super(collection, organism, template, createdBy);
    this.idSetId   = Validation.oneMinimum(idSetId);
    this.createdOn = Validation.nonNull(createdOn);
  }

  public IdSet(int idSetId, OffsetDateTime createdOn, NewIdSet from) {
    this(
      idSetId,
      from.getCollection(),
      from.getOrganism(),
      from.getTemplate(),
      from.getCreatedBy(),
      createdOn
    );
  }

  public int getIdSetId() {
    return idSetId;
  }

  public OffsetDateTime getCreatedOn() {
    return createdOn;
  }
}
