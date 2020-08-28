package org.veupathdb.service.osi.model.db;

import java.time.OffsetDateTime;

public class Organism
{
  private final long id;

  private final String name;

  private final String template;

  private final long geneCounterStart;

  private final long geneCounterCurrent;

  private final long transcriptCounterStart;

  private final long transcriptCounterCurrent;

  private final long createdBy;

  private final OffsetDateTime created;

  private final OffsetDateTime modified;

  public Organism(
    long id,
    String name,
    String template,
    long geneCounterStart,
    long geneCounterCurrent,
    long transcriptCounterStart,
    long transcriptCounterCurrent,
    long createdBy,
    OffsetDateTime created,
    OffsetDateTime modified
  ) {
    this.id                       = id;
    this.name                     = name;
    this.template                 = template;
    this.geneCounterStart         = geneCounterStart;
    this.geneCounterCurrent       = geneCounterCurrent;
    this.transcriptCounterStart   = transcriptCounterStart;
    this.transcriptCounterCurrent = transcriptCounterCurrent;
    this.createdBy                = createdBy;
    this.created                  = created;
    this.modified                 = modified;
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
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

  public long getCreatedBy() {
    return createdBy;
  }

  public OffsetDateTime getCreatedOn() {
    return created;
  }

  public OffsetDateTime getModified() {
    return modified;
  }
}
