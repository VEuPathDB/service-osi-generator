package org.veupathdb.service.osi.model.db;

import java.time.OffsetDateTime;

import org.veupathdb.service.osi.util.Validation;

public class Gene
{
  private final long geneId;

  private final long idSetId;

  private final String geneIdentifier;

  private final OffsetDateTime createdOn;

  private final long createdBy;

  public Gene(
    final long geneId,
    final long idSetId,
    final String geneIdentifier,
    final OffsetDateTime createdOn,
    final long createdBy
  ) {
    this.geneId         = Validation.oneMinimum(geneId);
    this.idSetId        = Validation.oneMinimum(idSetId);
    this.geneIdentifier = Validation.nonEmpty(geneIdentifier);
    this.createdOn      = Validation.nonNull(createdOn);
    this.createdBy      = Validation.oneMinimum(createdBy);
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
