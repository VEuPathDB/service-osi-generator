package org.veupathdb.service.osi.model.db;

import java.time.OffsetDateTime;

import org.veupathdb.service.osi.service.organism.OrganismUtil;
import org.veupathdb.service.osi.util.Errors;
import org.veupathdb.service.osi.util.Format;
import org.veupathdb.service.osi.util.Validation;

public class Organism
{
  private final long id;

  private final String name;

  private final String template;

  private final long geneCounterStart;

  private final long geneCounterCurrent;

  private final long transcriptCounterStart;

  private final long transcriptCounterCurrent;

  private final long createdBy;

  private final OffsetDateTime created;

  private final OffsetDateTime modified;

  public Organism(
    final long id,
    final String name,
    final String template,
    final long geneCounterStart,
    final long geneCounterCurrent,
    final long transcriptCounterStart,
    final long transcriptCounterCurrent,
    final OffsetDateTime created,
    final OffsetDateTime modified,
    final long createdBy
  ) {
    this.id                       = Validation.oneMinimum(id);
    this.name                     = Validation.nonEmpty(name);
    this.template                 = OrganismUtil.validateTemplate(template);
    this.geneCounterStart         = Validation.oneMinimum(geneCounterStart);
    this.geneCounterCurrent       = Validation.minimum(
      geneCounterStart,
      geneCounterCurrent);
    this.transcriptCounterStart   = Validation.oneMinimum(transcriptCounterStart);
    this.transcriptCounterCurrent = Validation.minimum(
      transcriptCounterStart,
      transcriptCounterCurrent);
    this.created                  = Validation.nonNull(created);
    this.modified                 = Validation.nonNull(modified);
    this.createdBy                = Validation.oneMinimum(createdBy);
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getTemplate() {
    return template;
  }

  public long getGeneCounterStart() {
    return geneCounterStart;
  }

  public long getGeneCounterCurrent() {
    return geneCounterCurrent;
  }

  public long getTranscriptCounterStart() {
    return transcriptCounterStart;
  }

  public long getTranscriptCounterCurrent() {
    return transcriptCounterCurrent;
  }

  public long getCreatedBy() {
    return createdBy;
  }

  public OffsetDateTime getCreatedOn() {
    return created;
  }

  public OffsetDateTime getModified() {
    return modified;
  }

  @Override
  public String toString() {
    return Errors.toRuntime(this, Format.Json()::writeValueAsString);
  }
}
