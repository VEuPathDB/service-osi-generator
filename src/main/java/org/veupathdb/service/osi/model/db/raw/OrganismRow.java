package org.veupathdb.service.osi.model.db.raw;

import java.time.OffsetDateTime;

import org.veupathdb.service.osi.model.db.Organism;
import org.veupathdb.service.osi.service.user.UserManager;

public class OrganismRow
{
  private final int id;

  private final String name;

  private final String template;

  private final long geneCounterStart;

  private final long geneCounterCurrent;

  private final long transcriptCounterStart;

  private final long transcriptCounterCurrent;

  private final int createdBy;

  private final OffsetDateTime created;

  private final OffsetDateTime modified;

  public OrganismRow(
    int id,
    String name,
    String template,
    long geneCounterStart,
    long geneCounterCurrent,
    long transcriptCounterStart,
    long transcriptCounterCurrent,
    int createdBy,
    OffsetDateTime created,
    OffsetDateTime modified
  ) {
    this.id                       = id;
    this.name                     = name;
    this.template                 = template;
    this.geneCounterStart         = geneCounterStart;
    this.geneCounterCurrent       = geneCounterCurrent;
    this.transcriptCounterStart   = transcriptCounterStart;
    this.transcriptCounterCurrent = transcriptCounterCurrent;
    this.createdBy                = createdBy;
    this.created                  = created;
    this.modified                 = modified;
  }

  public int getId() {
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

  public int getCreatedBy() {
    return createdBy;
  }

  public OffsetDateTime getCreated() {
    return created;
  }

  public OffsetDateTime getModified() {
    return modified;
  }

  public Organism toOrganism() throws Exception {
    return new Organism(
      id,
      name,
      template,
      geneCounterStart,
      geneCounterCurrent,
      transcriptCounterStart,
      transcriptCounterCurrent,
      UserManager.lookup(createdBy).orElseThrow(),
      created,
      modified
    );
  }
}
