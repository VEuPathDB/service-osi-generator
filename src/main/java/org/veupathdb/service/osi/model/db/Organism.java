package org.veupathdb.service.osi.model.db;

import java.time.OffsetDateTime;

public class Organism
{
  private int organismId;
  private final String template;
  private final long geneCounterStart;
  private final long geneCounterCurrent;
  private final long transcriptCounterStart;
  private final long transcriptCounterCurrent;
  private final User createdBy;
  private final OffsetDateTime created;
  private final OffsetDateTime modified;

  public Organism(
    String template,
    long geneCounterStart,
    long geneCounterCurrent,
    long transcriptCounterStart,
    long transcriptCounterCurrent,
    User createdBy,
    OffsetDateTime created,
    OffsetDateTime modified
  ) {
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

  public Organism setOrganismId(int organismId) {
    this.organismId = organismId;
    return this;
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

  public User getCreatedBy() {
    return createdBy;
  }

  public OffsetDateTime getCreated() {
    return created;
  }

  public OffsetDateTime getModified() {
    return modified;
  }
}
