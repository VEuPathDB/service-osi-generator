package org.veupathdb.service.osi.generated.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "collectionId",
    "organismId",
    "generateGenes"
})
public class IdSetPostRequestImpl implements IdSetPostRequest {
  @JsonProperty("collectionId")
  private long collectionId;

  @JsonProperty("organismId")
  private long organismId;

  @JsonProperty("generateGenes")
  private int generateGenes;

  @JsonProperty("collectionId")
  public long getCollectionId() {
    return this.collectionId;
  }

  @JsonProperty("collectionId")
  public void setCollectionId(long collectionId) {
    this.collectionId = collectionId;
  }

  @JsonProperty("organismId")
  public long getOrganismId() {
    return this.organismId;
  }

  @JsonProperty("organismId")
  public void setOrganismId(long organismId) {
    this.organismId = organismId;
  }

  @JsonProperty("generateGenes")
  public int getGenerateGenes() {
    return this.generateGenes;
  }

  @JsonProperty("generateGenes")
  public void setGenerateGenes(int generateGenes) {
    this.generateGenes = generateGenes;
  }
}
