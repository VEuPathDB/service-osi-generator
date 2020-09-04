package org.veupathdb.service.osi.model;

import java.time.OffsetDateTime;

import org.veupathdb.service.osi.util.Params;

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
    this.name = name == null ? null : name.toLowerCase().replaceAll("\\*", "%");
    return this;
  }

  public boolean hasName() {
    return name != null;
  }

  public OffsetDateTime getStart() {
    return start;
  }

  public RecordQuery setStart(final Long start) {
    this.start = Params.nullableTimestamp(start);
    return this;
  }

  public boolean hasStart() {
    return start != null;
  }

  public OffsetDateTime getEnd() {
    return end;
  }

  public RecordQuery setEnd(final Long end) {
    this.end = Params.nullableTimestamp(end);
    return this;
  }

  public boolean hasEnd() {
    return end != null;
  }

  public Long getCreatedById() {
    return createdById;
  }

  public boolean hasCreatedById() {
    return createdById != null;
  }

  public String getCreatedByName() {
    return createdByName;
  }

  public RecordQuery setCreatedBy(final String createdBy) {
    if (createdBy != null) {
      var either = Params.stringOrLong(createdBy);
      if (either.isLeft())
        createdByName = either.getLeft();
      else if (either.isRight())
        createdById = either.getRight();
    } else {
      createdByName = null;
      createdById = null;
    }
    return this;
  }

  public boolean hasCreatedByName() {
    return createdByName != null;
  }
}
