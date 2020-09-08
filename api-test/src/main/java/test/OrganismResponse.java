package test;

import java.time.OffsetDateTime;

public class OrganismResponse
{
  private Long           organismId;
  private String         organismName;
  private String         template;
  private Long           geneIntStart;
  private Long           geneIntCurrent;
  private Long           transcriptIntStart;
  private Long           transcriptIntCurrent;
  private OffsetDateTime createdOn;
  private Long           createdBy;

  public Long getOrganismId() {
    return organismId;
  }

  public OrganismResponse setOrganismId(Long organismId) {
    this.organismId = organismId;
    return this;
  }

  public String getOrganismName() {
    return organismName;
  }

  public OrganismResponse setOrganismName(String organismName) {
    this.organismName = organismName;
    return this;
  }

  public String getTemplate() {
    return template;
  }

  public OrganismResponse setTemplate(String template) {
    this.template = template;
    return this;
  }

  public Long getGeneIntStart() {
    return geneIntStart;
  }

  public OrganismResponse setGeneIntStart(Long geneIntStart) {
    this.geneIntStart = geneIntStart;
    return this;
  }

  public Long getGeneIntCurrent() {
    return geneIntCurrent;
  }

  public OrganismResponse setGeneIntCurrent(Long geneIntCurrent) {
    this.geneIntCurrent = geneIntCurrent;
    return this;
  }

  public Long getTranscriptIntStart() {
    return transcriptIntStart;
  }

  public OrganismResponse setTranscriptIntStart(Long transcriptIntStart) {
    this.transcriptIntStart = transcriptIntStart;
    return this;
  }

  public Long getTranscriptIntCurrent() {
    return transcriptIntCurrent;
  }

  public OrganismResponse setTranscriptIntCurrent(Long transcriptIntCurrent) {
    this.transcriptIntCurrent = transcriptIntCurrent;
    return this;
  }

  public OffsetDateTime getCreatedOn() {
    return createdOn;
  }

  public OrganismResponse setCreatedOn(OffsetDateTime createdOn) {
    this.createdOn = createdOn;
    return this;
  }

  public Long getCreatedBy() {
    return createdBy;
  }

  public OrganismResponse setCreatedBy(Long createdBy) {
    this.createdBy = createdBy;
    return this;
  }
}
