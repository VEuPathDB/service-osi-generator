package org.veupathdb.service.osi.model;

import java.time.OffsetDateTime;

public class RecordQuery
{
  private String name;
  private OffsetDateTime start;
  private OffsetDateTime end;
  private Long createdById;
  private String createdByName;

  public String getName() {
    return name;
  }

  public RecordQuery setName(String name) {
    this.name = name;
    return this;
  }

  public boolean hasName() {
    return name != null;
  }

  public OffsetDateTime getStart() {
    return start;
  }

  public RecordQuery setStart(OffsetDateTime start) {
    this.start = start;
    return this;
  }

  public boolean hasStart() {
    return start != null;
  }

  public OffsetDateTime getEnd() {
    return end;
  }

  public RecordQuery setEnd(OffsetDateTime end) {
    this.end = end;
    return this;
  }

  public boolean hasEnd() {
    return end != null;
  }

  public Long getCreatedById() {
    return createdById;
  }

  public void setCreatedById(Long createdById) {
    this.createdById = createdById;
  }

  public boolean hasCreatedById() {
    return createdById != null;
  }

  public String getCreatedByName() {
    return createdByName;
  }

  public void setCreatedByName(String createdByName) {
    this.createdByName = createdByName;
  }

  public boolean hasCreatedByName() {
    return createdByName != null;
  }
}
