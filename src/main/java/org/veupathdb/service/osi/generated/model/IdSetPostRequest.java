package org.veupathdb.service.osi.generated.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(
    as = IdSetPostRequestImpl.class
)
public interface IdSetPostRequest {
  @JsonProperty("organismId")
  long getOrganismId();

  @JsonProperty("organismId")
  void setOrganismId(long organismId);

  @JsonProperty("generateGenes")
  int getGenerateGenes();

  @JsonProperty("generateGenes")
  void setGenerateGenes(int generateGenes);
}
