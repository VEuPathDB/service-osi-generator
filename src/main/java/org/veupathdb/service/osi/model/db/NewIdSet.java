package org.veupathdb.service.osi.model.db;

import org.veupathdb.service.osi.model.db.raw.IdSetCollectionRow;
import org.veupathdb.service.osi.model.db.raw.OrganismRow;
import org.veupathdb.service.osi.util.Errors;
import org.veupathdb.service.osi.util.Format;
import org.veupathdb.service.osi.util.Validation;

public class NewIdSet
{
  private final IdSetCollectionRow collection;
  private final OrganismRow organism;
  private final String template;
  private final User createdBy;
  private final long counterStart;
  private final int numIssued;

  public NewIdSet(
    IdSetCollectionRow collection,
    OrganismRow organism,
    String template,
    User createdBy,
    long counterStart,
    int numIssued
  ) {
    this.collection = Validation.nonNull(collection);
    this.organism   = Validation.nonNull(organism);
    this.template   = Validation.nonEmpty(template);
    this.createdBy  = Validation.nonNull(createdBy);
    this.counterStart = Validation.oneMinimum(counterStart);
    this.numIssued  = Validation.zeroMinimum(numIssued);
  }

  public IdSetCollectionRow getCollection() {
    return collection;
  }

  public OrganismRow getOrganism() {
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
