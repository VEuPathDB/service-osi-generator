package org.veupathdb.service.osi.generated.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "idSetId",
    "collectionId",
    "template",
    "geneIntStart",
    "generatedGeneCount",
    "generatedIds",
    "created"
})
public class IdSetResponseImpl implements IdSetResponse {
  @JsonProperty("idSetId")
  private long idSetId;

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

  @JsonProperty("created")
  private long created;

  @JsonProperty("idSetId")
  public long getIdSetId() {
    return this.idSetId;
  }

  @JsonProperty("idSetId")
  public void setIdSetId(long idSetId) {
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

  @JsonProperty("created")
  public long getCreated() {
    return this.created;
  }

  @JsonProperty("created")
  public void setCreated(long created) {
    this.created = created;
  }
}
