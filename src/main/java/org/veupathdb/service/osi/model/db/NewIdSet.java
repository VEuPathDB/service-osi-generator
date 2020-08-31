package org.veupathdb.service.osi.model.db;

import org.veupathdb.service.osi.util.Errors;
import org.veupathdb.service.osi.util.Format;
import org.veupathdb.service.osi.util.Validation;

public class NewIdSet
{
  private final IdSetCollection collection;

  private final Organism organism;

  private final String template;

  private final User createdBy;

  private final long counterStart;

  private final int numIssued;

  public NewIdSet(
    final IdSetCollection collection,
    final Organism organism,
    final String template,
    final long counterStart,
    final int numIssued,
    final User createdBy
  ) {
    this.collection   = Validation.nonNull(collection);
    this.organism     = Validation.nonNull(organism);
    this.template     = Validation.nonEmpty(template);
    this.counterStart = Validation.oneMinimum(counterStart);
    this.numIssued    = Validation.zeroMinimum(numIssued);
    this.createdBy    = Validation.nonNull(createdBy);
  }

  public IdSetCollection getCollection() {
    return collection;
  }

  public Organism getOrganism() {
    return organism;
  }

  public String getTemplate() {
    return template;
  }

  public User getCreatedBy() {
    return createdBy;
  }

  public long getCounterStart() {
    return counterStart;
  }

  public int getNumIssued() {
    return numIssued;
  }

  @Override
  public String toString() {
    return Errors.toRuntime(this, Format.Json()::writeValueAsString);
  }
}
