package org.veupathdb.service.osi.generated.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(
    as = IdSetPostRequestImpl.class
)
public interface IdSetPostRequest {
  @JsonProperty("organismId")
  Long getOrganismId();

  @JsonProperty("organismId")
  void setOrganismId(Long organismId);

  @JsonProperty("generateGenes")
  Integer getGenerateGenes();

  @JsonProperty("generateGenes")
  void setGenerateGenes(Integer generateGenes);
}
