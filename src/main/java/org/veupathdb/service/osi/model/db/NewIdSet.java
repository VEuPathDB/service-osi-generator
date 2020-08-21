package org.veupathdb.service.osi.model.db;

public class NewIdSet
{
  private final IdSetCollection collection;
  private final Organism organism;
  private final String template;
  private final User createdBy;

  public NewIdSet(
    IdSetCollection collection,
    Organism organism,
    String template,
    User createdBy
  ) {
    this.collection = collection;
    this.organism   = organism;
    this.template   = template;
    this.createdBy  = createdBy;
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
}
