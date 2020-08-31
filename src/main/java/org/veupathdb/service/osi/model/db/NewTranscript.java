package org.veupathdb.service.osi.model.db;

import org.veupathdb.service.osi.util.Errors;
import org.veupathdb.service.osi.util.Format;
import org.veupathdb.service.osi.util.Validation;

public class NewTranscript
{
  private final Gene gene;

  private final long counterStart;

  private final int numIssued;

  private final User createdBy;

  public NewTranscript(
    final Gene gene,
    final long counterStart,
    final int numIssued,
    final User createdBy
  ) {
    this.gene         = Validation.nonNull(gene);
    this.counterStart = Validation.oneMinimum(counterStart);
    this.numIssued    = Validation.zeroMinimum(numIssued);
    this.createdBy    = Validation.nonNull(createdBy);
  }

  public Gene getGene() {
    return gene;
  }

  public long getCounterStart() {
    return counterStart;
  }

  public int getNumIssued() {
    return numIssued;
  }

  public User getCreatedBy() {
    return createdBy;
  }

  @Override
  public String toString() {
    return Errors.toRuntime(this, Format.Json()::writeValueAsString);
  }
}
