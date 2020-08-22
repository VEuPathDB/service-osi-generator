package org.veupathdb.service.osi.model.db;

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
    IdSetCollection collection,
    Organism organism,
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
    return "NewIdSet{" +
      "collection=" + collection.getCollectionId() +
      ", organism=" + organism.getOrganismId() +
      ", template='" + template + '\'' +
      ", createdBy=" + createdBy.getUserId() +
      ", counterStart=" + counterStart +
      ", numIssued=" + numIssued +
      '}';
  }
}
