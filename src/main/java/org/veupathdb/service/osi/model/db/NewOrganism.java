package org.veupathdb.service.osi.model.db;

import org.veupathdb.service.osi.util.Validation;

public class NewOrganism
{
  private final String template;
  private final long geneCounterStart;
  private final long transcriptCounterStart;
  private final User createdBy;

  public NewOrganism(
    String template,
    long geneCounterStart,
    long transcriptCounterStart,
    User createdBy
  ) {
    this.template                 = Validation.nonEmpty(template);
    this.geneCounterStart         = Validation.oneMinimum(geneCounterStart);
    this.transcriptCounterStart   = Validation.oneMinimum(transcriptCounterStart);
    this.createdBy                = createdBy;
  }

  public String getTemplate() {
    return template;
  }

  public long getGeneCounterStart() {
    return geneCounterStart;
  }

  public long getTranscriptCounterStart() {
    return transcriptCounterStart;
  }

  public User getCreatedBy() {
    return createdBy;
  }
}
