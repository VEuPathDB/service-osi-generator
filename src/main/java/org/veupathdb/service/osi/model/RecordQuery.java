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

  public RecordQuery setName(final String name) {
    this.name = name;
    return this;
  }

  public boolean hasName() {
    return name != null;
  }

  public OffsetDateTime getStart() {
    return start;
  }

  public RecordQuery setStart(final OffsetDateTime start) {
    this.start = start;
    return this;
  }

  public boolean hasStart() {
    return start != null;
  }

  public OffsetDateTime getEnd() {
    return end;
  }

  public RecordQuery setEnd(final OffsetDateTime end) {
    this.end = end;
    return this;
  }

  public boolean hasEnd() {
    return end != null;
  }

  public Long getCreatedById() {
    return createdById;
  }

  public RecordQuery setCreatedById(final Long createdById) {
    this.createdById = createdById;
    return this;
  }

  public boolean hasCreatedById() {
    return createdById != null;
  }

  public String getCreatedByName() {
    return createdByName;
  }

  public RecordQuery setCreatedByName(final String createdByName) {
    this.createdByName = createdByName;
    return this;
  }

  public boolean hasCreatedByName() {
    return createdByName != null;
  }
}
