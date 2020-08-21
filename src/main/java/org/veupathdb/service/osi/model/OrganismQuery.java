package org.veupathdb.service.osi.model;

import java.time.OffsetDateTime;

import org.veupathdb.service.osi.model.db.User;

public class OrganismQuery
{
  private String name;
  private OffsetDateTime start;
  private OffsetDateTime end;
  private Integer createdById;
  private String createdByName;

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

  public Integer getCreatedById() {
    return createdById;
  }

  public void setCreatedById(Integer createdById) {
    this.createdById = createdById;
  }

  public String getCreatedByName() {
    return createdByName;
  }

  public void setCreatedByName(String createdByName) {
    this.createdByName = createdByName;
  }
}
