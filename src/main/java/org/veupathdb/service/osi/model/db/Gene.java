package org.veupathdb.service.osi.model.db;

import java.time.OffsetDateTime;

import org.veupathdb.service.osi.util.Validation;

public class Gene extends NewGene
{
  private final int geneId;

  private final OffsetDateTime createdOn;

  public Gene(
    int geneId,
    IdSet idSet,
    String identifier,
    User createdBy,
    OffsetDateTime createdOn
  ) {
    super(idSet, identifier, createdBy);

    this.geneId = Validation.oneMinimum(geneId);
    this.createdOn = Validation.nonNull(createdOn);
  }

  public Gene(
    int geneId,
    OffsetDateTime createdOn,
    NewGene from
  ) {
    this (
      geneId,
      from.getIdSet(),
      from.getIdentifier(),
      from.getCreatedBy(),
      createdOn
    );
  }

  public int getGeneId() {
    return geneId;
  }

  public OffsetDateTime getCreatedOn() {
    return createdOn;
  }
}
