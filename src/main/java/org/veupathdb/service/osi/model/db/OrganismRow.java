package org.veupathdb.service.osi.model.db;

import java.time.OffsetDateTime;

import org.veupathdb.service.osi.util.Validation;

public class OrganismRow
{
  private final int organismId;
  private final String template;
  private final long geneCounterStart;
  private final long geneCounterCurrent;
  private final long transcriptCounterStart;
  private final long transcriptCounterCurrent;
  private final int createdBy;
  private final OffsetDateTime created;
  private final OffsetDateTime modified;

  public OrganismRow(
    int organismId,
    String template,
    long geneCounterStart,
    long geneCounterCurrent,
    long transcriptCounterStart,
    long transcriptCounterCurrent,
    int createdBy,
    OffsetDateTime created,
    OffsetDateTime modified
  ) {
    this.organismId               = organismId;
    this.template                 = template;
    this.geneCounterStart         = geneCounterStart;
    this.geneCounterCurrent       = geneCounterCurrent;
    this.transcriptCounterStart   = transcriptCounterStart;
    this.transcriptCounterCurrent = transcriptCounterCurrent;
    this.createdBy                = createdBy;
    this.created                  = created;
    this.modified                 = modified;
  }

  public int getOrganismId() {
    return organismId;
  }

  public String getTemplate() {
    return template;
  }

  public long getGeneCounterStart() {
    return geneCounterStart;
  }

  public long getGeneCounterCurrent() {
    return geneCounterCurrent;
  }

  public long getTranscriptCounterStart() {
    return transcriptCounterStart;
  }

  public long getTranscriptCounterCurrent() {
    return transcriptCounterCurrent;
  }

  public int getCreatedBy() {
    return createdBy;
  }

  public OffsetDateTime getCreated() {
    return created;
  }

  public OffsetDateTime getModified() {
    return modified;
  }
}
