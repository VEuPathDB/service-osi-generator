package org.veupathdb.service.osi.generated.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "organismId",
    "generateGenes"
})
public class IdSetPostRequestImpl implements IdSetPostRequest {
  @JsonProperty("organismId")
  private Long organismId;

  @JsonProperty("generateGenes")
  private Integer generateGenes;

  @JsonProperty("organismId")
  public Long getOrganismId() {
    return this.organismId;
  }

  @JsonProperty("organismId")
  public void setOrganismId(Long organismId) {
    this.organismId = organismId;
  }

  @JsonProperty("generateGenes")
  public Integer getGenerateGenes() {
    return this.generateGenes;
  }

  @JsonProperty("generateGenes")
  public void setGenerateGenes(Integer generateGenes) {
    this.generateGenes = generateGenes;
  }
}
