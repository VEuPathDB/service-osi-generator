package org.veupathdb.service.osi.model.db;

import java.time.OffsetDateTime;

import org.veupathdb.service.osi.model.db.raw.GeneRow;
import org.veupathdb.service.osi.util.Validation;

public class Transcript extends NewTranscript
{
  private final long transcriptId;

  private final OffsetDateTime createdOn;

  public Transcript(
    long transcriptId,
    GeneRow gene,
    long counterStart,
    int numIssued,
    User createdBy,
    OffsetDateTime createdOn
  ) {
    super(gene, counterStart, numIssued, createdBy);
    this.transcriptId = transcriptId;
    this.createdOn    = Validation.nonNull(createdOn);
  }

  public Transcript(
    long transcriptId,
    OffsetDateTime createdOn,
    NewTranscript from
  ) {
    this(
      transcriptId,
      from.getGene(),
      from.getCounterStart(),
      from.getNumIssued(),
      from.getCreatedBy(),
      createdOn
    );
  }

  public long getTranscriptId() {
    return transcriptId;
  }

  public OffsetDateTime getCreatedOn() {
    return createdOn;
  }
}
