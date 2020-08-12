package org.veupathdb.service.osi.generated.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Map;

@JsonDeserialize(
    as = IdSetPostRequestImpl.class
)
public interface IdSetPostRequest {
  @JsonAnyGetter
  Map<String, Object> getAdditionalProperties();

  @JsonAnySetter
  void setAdditionalProperties(String key, Object value);

  @JsonProperty("organismId")
  long getOrganismId();

  @JsonProperty("organismId")
  void setOrganismId(long organismId);

  @JsonProperty("generateGenes")
  int getGenerateGenes();

  @JsonProperty("generateGenes")
  void setGenerateGenes(int generateGenes);
}
