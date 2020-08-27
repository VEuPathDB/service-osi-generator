package org.veupathdb.service.osi.model.db.raw;

import java.time.OffsetDateTime;

import org.veupathdb.service.osi.util.Validation;

public class TranscriptRow
{
  private final long transcriptId;
  private final long geneId;
  private final long counterStart;
  private final int numIssued;
  private final OffsetDateTime createdOn;
  private final long createdBy;

  public TranscriptRow(
    long transcriptId,
    long geneId,
    long counterStart,
    int numIssued,
    OffsetDateTime createdOn,
    long createdBy
  ) {
    this.transcriptId = Validation.oneMinimum(transcriptId);
    this.geneId       = Validation.oneMinimum(geneId);
    this.counterStart = Validation.oneMinimum(counterStart);
    this.numIssued    = Validation.zeroMinimum(numIssued);
    this.createdOn    = Validation.nonNull(createdOn);
    this.createdBy    = Validation.oneMinimum(createdBy);
  }

  public long getTranscriptId() {
    return transcriptId;
  }

  public long getGeneId() {
    return geneId;
  }

  public long getCounterStart() {
    return counterStart;
  }

  public int getNumIssued() {
    return numIssued;
  }

  public OffsetDateTime getCreatedOn() {
    return createdOn;
  }

  public long getCreatedBy() {
    return createdBy;
  }
}