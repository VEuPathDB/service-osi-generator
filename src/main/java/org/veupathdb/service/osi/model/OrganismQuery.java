package org.veupathdb.service.osi.model;

import java.time.OffsetDateTime;

import org.veupathdb.service.osi.model.db.User;

public class OrganismQuery
{
  private String name;
  private OffsetDateTime start;
  private OffsetDateTime end;
  private User createdBy;

  public String getName() {
    return name;
  }

  public OrganismQuery setName(String name) {
    this.name = name;
    return this;
  }

  public OffsetDateTime getStart() {
    return start;
  }

  public OrganismQuery setStart(OffsetDateTime start) {
    this.start = start;
    return this;
  }

  public OffsetDateTime getEnd() {
    return end;
  }

  public OrganismQuery setEnd(OffsetDateTime end) {
    this.end = end;
    return this;
  }

  public User getCreatedBy() {
    return createdBy;
  }

  public OrganismQuery setCreatedBy(User createdBy) {
    this.createdBy = createdBy;
    return this;
  }
}
