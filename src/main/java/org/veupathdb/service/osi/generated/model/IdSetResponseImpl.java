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
  private Long idSetId;

  @JsonProperty("organismId")
  private Long organismId;

  @JsonProperty("template")
  private String template;

  @JsonProperty("geneIntStart")
  private Long geneIntStart;

  @JsonProperty("generatedGeneCount")
  private Integer generatedGeneCount;

  @JsonProperty("generatedIds")
  private List<GeneratedTranscriptEntry> generatedIds;

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

  @JsonProperty("idSetId")
  public Long getIdSetId() {
    return this.idSetId;
  }

  @JsonProperty("idSetId")
  public void setIdSetId(Long idSetId) {
    this.idSetId = idSetId;
  }

  @JsonProperty("organismId")
  public Long getOrganismId() {
    return this.organismId;
  }

  @JsonProperty("organismId")
  public void setOrganismId(Long organismId) {
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
  public Long getGeneIntStart() {
    return this.geneIntStart;
  }

  @JsonProperty("geneIntStart")
  public void setGeneIntStart(Long geneIntStart) {
    this.geneIntStart = geneIntStart;
  }

  @JsonProperty("generatedGeneCount")
  public Integer getGeneratedGeneCount() {
    return this.generatedGeneCount;
  }

  @JsonProperty("generatedGeneCount")
  public void setGeneratedGeneCount(Integer generatedGeneCount) {
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
  public Long getCreatedBy() {
    return this.createdBy;
  }

  @JsonProperty("createdBy")
  public void setCreatedBy(Long createdBy) {
    this.createdBy = createdBy;
  }
}
