package org.veupathdb.service.osi.model.db;

import java.time.OffsetDateTime;

import org.veupathdb.service.osi.util.Errors;
import org.veupathdb.service.osi.util.Format;
import org.veupathdb.service.osi.util.Validation;

/**
 * Immutable representation of an expanded Organism record containing references
 * to immutable representations of all parent/depended records.
 */
public class Organism extends NewOrganism
{
  private final long id;

  private final long geneCountCur;

  private final long tranCountCur;

  private final OffsetDateTime created;

  private final OffsetDateTime modified;

  public Organism(
    long id,
    String name,
    String template,
    long geneCountStart,
    long geneCountCurrent,
    long transcriptCountStart,
    long transcriptCountCurrent,
    User createdBy,
    OffsetDateTime created,
    OffsetDateTime modified
  ) {
    super(name, template, geneCountStart, transcriptCountStart, createdBy);

    this.id           = Validation.oneMinimum(id);
    this.geneCountCur = Validation.setMinimum(geneCountCurrent, geneCountStart);
    this.tranCountCur = Validation.setMinimum(
      transcriptCountCurrent,
      transcriptCountStart
    );
    this.created      = Validation.nonNull(created);
    this.modified     = Validation.nonNull(modified);
  }

  public Organism(
    int id,
    long geneCounterCurrent,
    long transcriptCounterCurrent,
    OffsetDateTime created,
    OffsetDateTime modified,
    NewOrganism from
  ) {
    this(
      id,
      from.getName(),
      from.getTemplate(),
      from.getGeneCounterStart(),
      geneCounterCurrent,
      from.getTranscriptCounterStart(),
      transcriptCounterCurrent,
      from.getCreatedBy(),
      created,
      modified
    );
  }

  public long getId() {
    return id;
  }

  public long getGeneCounterCurrent() {
    return geneCountCur;
  }

  public long getTranscriptCounterCurrent() {
    return tranCountCur;
  }

  public OffsetDateTime getCreatedOn() {
    return created;
  }

  public OffsetDateTime getModified() {
    return modified;
  }

  public Organism incrementGeneCounter(int by) {
    return new Organism(
      getId(),
      getName(),
      getTemplate(),
      getGeneCounterStart(),
      getGeneCounterCurrent() + by,
      getTranscriptCounterStart(),
      getTranscriptCounterCurrent(),
      getCreatedBy(),
      getCreatedOn(),
      getModified()
    );
  }

  public Organism incrementTranscriptCounter(int by) {
    return new Organism(
      getId(),
      getName(),
      getTemplate(),
      getGeneCounterStart(),
      getGeneCounterCurrent(),
      getTranscriptCounterStart(),
      getTranscriptCounterCurrent() + by,
      getCreatedBy(),
      getCreatedOn(),
      getModified()
    );
  }

  @Override
  public String toString() {
    return Errors.toRuntime(this, Format.Json()::writeValueAsString);
  }
}
