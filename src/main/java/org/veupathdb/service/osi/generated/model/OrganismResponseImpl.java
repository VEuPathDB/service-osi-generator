package org.veupathdb.service.osi.generated.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "organismId",
    "organismName",
    "template",
    "geneIntStart",
    "transcriptIntStart",
    "geneIntCurrent",
    "transcriptIntCurrent",
    "createdOn",
    "createdBy"
})
public class OrganismResponseImpl implements OrganismResponse {
  @JsonProperty("organismId")
  private Long organismId;

  @JsonProperty("organismName")
  private String organismName;

  @JsonProperty("template")
  private String template;

  @JsonProperty("geneIntStart")
  private Long geneIntStart;

  @JsonProperty("transcriptIntStart")
  private Long transcriptIntStart;

  @JsonProperty("geneIntCurrent")
  private Long geneIntCurrent;

  @JsonProperty("transcriptIntCurrent")
  private Long transcriptIntCurrent;

  @JsonProperty("createdOn")
  @JsonFormat(
      shape = JsonFormat.Shape.STRING,
      pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"
  )
  @JsonDeserialize(
      using = TimestampDeserializer.class
  )
  private Date createdOn;

  @JsonProperty("createdBy")
  private Long createdBy;

  @JsonProperty("organismId")
  public Long getOrganismId() {
    return this.organismId;
  }

  @JsonProperty("organismId")
  public void setOrganismId(Long organismId) {
    this.organismId = organismId;
  }

  @JsonProperty("organismName")
  public String getOrganismName() {
    return this.organismName;
  }

  @JsonProperty("organismName")
  public void setOrganismName(String organismName) {
    this.organismName = organismName;
  }

  @JsonProperty("template")
  public String getTemplate() {
    return this.template;
  }

  @JsonProperty("template")
  public void setTemplate(String template) {
    this.template = template;
  }

  @JsonProperty("geneIntStart")
  public Long getGeneIntStart() {
    return this.geneIntStart;
  }

  @JsonProperty("geneIntStart")
  public void setGeneIntStart(Long geneIntStart) {
    this.geneIntStart = geneIntStart;
  }

  @JsonProperty("transcriptIntStart")
  public Long getTranscriptIntStart() {
    return this.transcriptIntStart;
  }

  @JsonProperty("transcriptIntStart")
  public void setTranscriptIntStart(Long transcriptIntStart) {
    this.transcriptIntStart = transcriptIntStart;
  }

  @JsonProperty("geneIntCurrent")
  public Long getGeneIntCurrent() {
    return this.geneIntCurrent;
  }

  @JsonProperty("geneIntCurrent")
  public void setGeneIntCurrent(Long geneIntCurrent) {
    this.geneIntCurrent = geneIntCurrent;
  }

  @JsonProperty("transcriptIntCurrent")
  public Long getTranscriptIntCurrent() {
    return this.transcriptIntCurrent;
  }

  @JsonProperty("transcriptIntCurrent")
  public void setTranscriptIntCurrent(Long transcriptIntCurrent) {
    this.transcriptIntCurrent = transcriptIntCurrent;
  }

  @JsonProperty("createdOn")
  public Date getCreatedOn() {
    return this.createdOn;
  }

  @JsonProperty("createdOn")
  public void setCreatedOn(Date createdOn) {
    this.createdOn = createdOn;
  }

  @JsonProperty("createdBy")
  public Long getCreatedBy() {
    return this.createdBy;
  }

  @JsonProperty("createdBy")
  public void setCreatedBy(Long createdBy) {
    this.createdBy = createdBy;
  }
}
