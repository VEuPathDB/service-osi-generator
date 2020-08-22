package org.veupathdb.service.osi.model.db.raw;

import java.time.OffsetDateTime;

public class IdSetRow
{
  private final int idSetId;

  private final int collectionId;

  private final int organismId;

  private final String template;

  private final OffsetDateTime created;

  private final int createdBy;

  public IdSetRow(
    int idSetId,
    int collectionId,
    int organismId,
    String template,
    OffsetDateTime created,
    int createdBy
  ) {
    this.idSetId      = idSetId;
    this.collectionId = collectionId;
    this.organismId   = organismId;
    this.template     = template;
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

  public OffsetDateTime getCreated() {
    return created;
  }

  public int getCreatedBy() {
    return createdBy;
  }
}
