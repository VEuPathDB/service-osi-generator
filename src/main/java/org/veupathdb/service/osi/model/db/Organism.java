package org.veupathdb.service.osi.model.db;

import java.time.OffsetDateTime;

import org.veupathdb.service.osi.util.Validation;

public class Organism extends NewOrganism
{
  private final int organismId;
  private final long geneCounterCurrent;
  private final long transcriptCounterCurrent;
  private final OffsetDateTime created;
  private final OffsetDateTime modified;

  public Organism(
    int organismId,
    String template,
    long geneCounterStart,
    long geneCounterCurrent,
    long transcriptCounterStart,
    long transcriptCounterCurrent,
    User createdBy,
    OffsetDateTime created,
    OffsetDateTime modified
  ) {
    super(template, geneCounterStart, transcriptCounterStart, createdBy);
    this.organismId               = Validation.oneMinimum(organismId);
    this.geneCounterCurrent       = Validation.setMinimum(geneCounterCurrent, geneCounterStart);
    this.transcriptCounterCurrent = Validation.setMinimum(transcriptCounterCurrent, transcriptCounterStart);
    this.created                  = Validation.nonNull(created);
    this.modified                 = Validation.nonNull(modified);
  }

  public Organism(
    int organismId,
    long geneCounterCurrent,
    long transcriptCounterCurrent,
    OffsetDateTime created,
    OffsetDateTime modified,
    NewOrganism from
  ) {
    this(
      organismId,
      from.getTemplate(),
      from.getGeneCounterStart(),
      geneCounterCurrent,
      from.getTranscriptCounterStart(),
      transcriptCounterCurrent,
      from.getCreatedBy(),
      created,
      modified
    );
  }

  public int getOrganismId() {
    return organismId;
  }

  public long getGeneCounterCurrent() {
    return geneCounterCurrent;
  }

  public long getTranscriptCounterCurrent() {
    return transcriptCounterCurrent;
  }

  public OffsetDateTime getCreated() {
    return created;
  }

  public OffsetDateTime getModified() {
    return modified;
  }
}
