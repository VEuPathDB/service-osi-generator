package org.veupathdb.service.osi.model.db;

import java.time.OffsetDateTime;

import org.veupathdb.service.osi.util.Validation;

public class Transcript extends NewTranscript
{
  private final OffsetDateTime createdOn;

  public Transcript(
    Gene gene,
    long counterStart,
    int numIssued,
    User createdBy,
    OffsetDateTime createdOn
  ) {
    super(gene, counterStart, numIssued, createdBy);
    this.createdOn = Validation.nonNull(createdOn);
  }

  public Transcript(OffsetDateTime createdOn, NewTranscript from) {
    this(
      from.getGene(),
      from.getCounterStart(),
      from.getNumIssued(),
      from.getCreatedBy(),
      createdOn
    );
  }

  public OffsetDateTime getCreatedOn() {
    return createdOn;
  }
}
