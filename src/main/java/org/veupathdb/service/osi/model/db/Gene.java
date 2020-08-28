package org.veupathdb.service.osi.model.db;

import java.time.OffsetDateTime;

public class Gene
{
  private final long geneId;

  private final long idSetId;

  private final String geneIdentifier;

  private final OffsetDateTime createdOn;

  private final long createdBy;

  public Gene(
    long geneId,
    long idSetId,
    String geneIdentifier,
    OffsetDateTime createdOn,
    long createdBy
  ) {
    this.geneId         = geneId;
    this.idSetId        = idSetId;
    this.geneIdentifier = geneIdentifier;
    this.createdOn      = createdOn;
    this.createdBy      = createdBy;
  }

  public long getId() {
    return geneId;
  }

  public long getIdSetId() {
    return idSetId;
  }

  public String getGeneIdentifier() {
    return geneIdentifier;
  }

  public OffsetDateTime getCreatedOn() {
    return createdOn;
  }

  public long getCreatedBy() {
    return createdBy;
  }
}
