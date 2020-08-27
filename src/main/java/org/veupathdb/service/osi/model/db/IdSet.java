package org.veupathdb.service.osi.model.db;

import java.time.OffsetDateTime;

import org.veupathdb.service.osi.util.Validation;

public class IdSet extends NewIdSet
{
  private final long idSetId;

  private final OffsetDateTime createdOn;

  public IdSet(
    long idSetId,
    IdSetCollection collection,
    Organism organism,
    String template,
    User createdBy,
    long counterStart,
    int numIssued,
    OffsetDateTime createdOn
  ) {
    super(collection, organism, template, createdBy, counterStart, numIssued);
    this.idSetId   = Validation.oneMinimum(idSetId);
    this.createdOn = Validation.nonNull(createdOn);
  }

  public IdSet(long idSetId, OffsetDateTime createdOn, NewIdSet from) {
    this(
      idSetId,
      from.getCollection(),
      from.getOrganism(),
      from.getTemplate(),
      from.getCreatedBy(),
      from.getCounterStart(),
      from.getNumIssued(),
      createdOn
    );
  }

  public long getId() {
    return idSetId;
  }

  public OffsetDateTime getCreatedOn() {
    return createdOn;
  }
}
