package test;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class OrganismResponse
{
  private Long           organism_id;
  private String         name;
  private String         template;
  private Long           gene_counter_start;
  private Long           gene_counter_current;
  private Long           transcript_counter_start;
  private Long           transcript_counter_current;
  private OffsetDateTime created;
  private Long           created_by;
  private OffsetDateTime modified;

  @JsonGetter("organismId")
  public Long getOrganismId() {
    return organism_id;
  }

  @JsonSetter("organismId")
  public OrganismResponse setOrganismId(Long organismId) {
    this.organism_id = organismId;
    return this;
  }

  @JsonGetter("organismName")
  public String getOrganismName() {
    return name;
  }

  @JsonSetter("organismName")
  public OrganismResponse setOrganismName(String organismName) {
    this.name = organismName;
    return this;
  }

  @JsonGetter("template")
  public String getTemplate() {
    return template;
  }

  @JsonSetter("template")
  public OrganismResponse setTemplate(String template) {
    this.template = template;
    return this;
  }

  @JsonGetter("geneIntStart")
  public Long getGeneIntStart() {
    return gene_counter_start;
  }

  @JsonSetter("geneIntStart")
  public OrganismResponse setGeneIntStart(Long geneIntStart) {
    this.gene_counter_start = geneIntStart;
    return this;
  }

  @JsonGetter("geneIntCurrent")
  public Long getGeneIntCurrent() {
    return gene_counter_current;
  }

  @JsonSetter("geneIntCurrent")
  public OrganismResponse setGeneIntCurrent(Long geneIntCurrent) {
    this.gene_counter_current = geneIntCurrent;
    return this;
  }

  @JsonGetter("transcriptIntStart")
  public Long getTranscriptIntStart() {
    return transcript_counter_start;
  }

  @JsonSetter("transcriptIntStart")
  public OrganismResponse setTranscriptIntStart(Long transcriptIntStart) {
    this.transcript_counter_start = transcriptIntStart;
    return this;
  }

  @JsonGetter("transcriptIntCurrent")
  public Long getTranscriptIntCurrent() {
    return transcript_counter_current;
  }

  @JsonSetter("transcriptIntCurrent")
  public OrganismResponse setTranscriptIntCurrent(Long transcriptIntCurrent) {
    this.transcript_counter_current = transcriptIntCurrent;
    return this;
  }

  @JsonGetter("createdOn")
  public OffsetDateTime getCreatedOn() {
    return created;
  }

  @JsonSetter("createdOn")
  public OrganismResponse setCreatedOn(OffsetDateTime createdOn) {
    this.created = createdOn;
    return this;
  }

  @JsonGetter("createdBy")
  public Long getCreatedBy() {
    return created_by;
  }

  @JsonSetter("createdBy")
  public OrganismResponse setCreatedBy(Long createdBy) {
    this.created_by = createdBy;
    return this;
  }
}
