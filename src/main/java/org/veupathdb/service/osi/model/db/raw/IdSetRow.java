package org.veupathdb.service.osi.model.db.raw;

import java.time.OffsetDateTime;

public class IdSetRow
{
  private final int idSetId;

  private final int collectionId;

  private final int organismId;

  private final String template;

  private final long counterStart;

  private final int numIssued;

  private final OffsetDateTime created;

  private final int createdBy;

  public IdSetRow(
    int idSetId,
    int collectionId,
    int organismId,
    String template,
    long counterStart,
    int numIssued,
    OffsetDateTime created,
    int createdBy
  ) {
    this.idSetId      = idSetId;
    this.collectionId = collectionId;
    this.organismId   = organismId;
    this.template     = template;
    this.counterStart = counterStart;
    this.numIssued    = numIssued;
    this.created      = created;
    this.createdBy    = createdBy;
  }

  public int getIdSetId() {
    return idSetId;
  }

  public int getCollectionId() {
    return collectionId;
  }

  public int getOrganismId() {
    return organismId;
  }

  public String getTemplate() {
    return template;
  }

  public long getCounterStart() {
    return counterStart;
  }

  public int getNumIssued() {
    return numIssued;
  }

  public OffsetDateTime getCreated() {
    return created;
  }

  public int getCreatedBy() {
    return createdBy;
  }
}
