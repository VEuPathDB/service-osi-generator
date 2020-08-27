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
    "collectionId",
    "template",
    "geneIntStart",
    "generatedGeneCount",
    "generatedIds",
    "createdOn",
    "createdBy"
})
public class IdSetResponseImpl implements IdSetResponse {
  @JsonProperty("idSetId")
  private int idSetId;

  @JsonProperty("collectionId")
  private int collectionId;

  @JsonProperty("template")
  private String template;

  @JsonProperty("geneIntStart")
  private long geneIntStart;

  @JsonProperty("generatedGeneCount")
  private int generatedGeneCount;

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
  private int createdBy;

  @JsonProperty("idSetId")
  public int getIdSetId() {
    return this.idSetId;
  }

  @JsonProperty("idSetId")
  public void setIdSetId(int idSetId) {
    this.idSetId = idSetId;
  }

  @JsonProperty("collectionId")
  public int getCollectionId() {
    return this.collectionId;
  }

  @JsonProperty("collectionId")
  public void setCollectionId(int collectionId) {
    this.collectionId = collectionId;
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
  public int getCreatedBy() {
    return this.createdBy;
  }

  @JsonProperty("createdBy")
  public void setCreatedBy(int createdBy) {
    this.createdBy = createdBy;
  }
}
