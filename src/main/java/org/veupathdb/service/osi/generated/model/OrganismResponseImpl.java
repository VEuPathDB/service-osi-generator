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
  private long organismId;

  @JsonProperty("organismName")
  private String organismName;

  @JsonProperty("template")
  private String template;

  @JsonProperty("geneIntStart")
  private long geneIntStart;

  @JsonProperty("transcriptIntStart")
  private long transcriptIntStart;

  @JsonProperty("geneIntCurrent")
  private long geneIntCurrent;

  @JsonProperty("transcriptIntCurrent")
  private long transcriptIntCurrent;

  @JsonFormat(
      shape = JsonFormat.Shape.STRING,
      pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"
  )
  @JsonDeserialize(
      using = TimestampDeserializer.class
  )
  @JsonProperty("createdOn")
  private Date createdOn;

  @JsonProperty("createdBy")
  private long createdBy;

  @JsonProperty("organismId")
  public long getOrganismId() {
    return this.organismId;
  }

  @JsonProperty("organismId")
  public void setOrganismId(long organismId) {
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
  public long getGeneIntStart() {
    return this.geneIntStart;
  }

  @JsonProperty("geneIntStart")
  public void setGeneIntStart(long geneIntStart) {
    this.geneIntStart = geneIntStart;
  }

  @JsonProperty("transcriptIntStart")
  public long getTranscriptIntStart() {
    return this.transcriptIntStart;
  }

  @JsonProperty("transcriptIntStart")
  public void setTranscriptIntStart(long transcriptIntStart) {
    this.transcriptIntStart = transcriptIntStart;
  }

  @JsonProperty("geneIntCurrent")
  public long getGeneIntCurrent() {
    return this.geneIntCurrent;
  }

  @JsonProperty("geneIntCurrent")
  public void setGeneIntCurrent(long geneIntCurrent) {
    this.geneIntCurrent = geneIntCurrent;
  }

  @JsonProperty("transcriptIntCurrent")
  public long getTranscriptIntCurrent() {
    return this.transcriptIntCurrent;
  }

  @JsonProperty("transcriptIntCurrent")
  public void setTranscriptIntCurrent(long transcriptIntCurrent) {
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
  public long getCreatedBy() {
    return this.createdBy;
  }

  @JsonProperty("createdBy")
  public void setCreatedBy(long createdBy) {
    this.createdBy = createdBy;
  }
}
