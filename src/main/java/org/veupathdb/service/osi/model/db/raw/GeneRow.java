package org.veupathdb.service.osi.model.db.raw;

import java.sql.ResultSet;
import java.time.OffsetDateTime;

public class GeneRow
{
  private final int geneId;

  private final int idSetId;

  private final String geneIdentifier;

  private final OffsetDateTime createdOn;

  private final int createdBy;

  public GeneRow(
    int geneId,
    int idSetId,
    String geneIdentifier,
    OffsetDateTime createdOn,
    int createdBy
  ) {
    this.geneId         = geneId;
    this.idSetId        = idSetId;
    this.geneIdentifier = geneIdentifier;
    this.createdOn      = createdOn;
    this.createdBy      = createdBy;
  }

  public int getGeneId() {
    return geneId;
  }

  public int getIdSetId() {
    return idSetId;
  }

  public String getGeneIdentifier() {
    return geneIdentifier;
  }

  public OffsetDateTime getCreatedOn() {
    return createdOn;
  }

  public int getCreatedBy() {
    return createdBy;
  }
}
