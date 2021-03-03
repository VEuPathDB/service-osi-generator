package org.veupathdb.service.osi.generated.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "idSetId",
    "organismId",
    "template",
    "geneIntStart",
    "generatedGeneCount",
    "generatedIds",
    "createdOn",
    "createdBy"
})
public class IdSetResponseImpl implements IdSetResponse {
  @JsonProperty("idSetId")
  private long idSetId;

  @JsonProperty("organismId")
  private long organismId;

  @JsonProperty("template")
  private String template;

  @JsonProperty("geneIntStart")
  private long geneIntStart;

  @JsonProperty("generatedGeneCount")
  private int generatedGeneCount;

  @JsonProperty("generatedIds")
  private List<GeneratedTranscriptEntry> generatedIds;

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

  @JsonProperty("idSetId")
  public long getIdSetId() {
    return this.idSetId;
  }

  @JsonProperty("idSetId")
  public void setIdSetId(long idSetId) {
    this.idSetId = idSetId;
  }

  @JsonProperty("organismId")
  public long getOrganismId() {
    return this.organismId;
  }

  @JsonProperty("organismId")
  public void setOrganismId(long organismId) {
    this.organismId = organismId;
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

  @JsonProperty("generatedGeneCount")
  public int getGeneratedGeneCount() {
    return this.generatedGeneCount;
  }

  @JsonProperty("generatedGeneCount")
  public void setGeneratedGeneCount(int generatedGeneCount) {
    this.generatedGeneCount = generatedGeneCount;
  }

  @JsonProperty("generatedIds")
  public List<GeneratedTranscriptEntry> getGeneratedIds() {
    return this.generatedIds;
  }

  @JsonProperty("generatedIds")
  public void setGeneratedIds(List<GeneratedTranscriptEntry> generatedIds) {
    this.generatedIds = generatedIds;
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
