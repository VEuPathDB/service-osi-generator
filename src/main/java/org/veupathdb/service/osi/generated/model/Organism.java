package org.veupathdb.service.osi.generated.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Map;

@JsonDeserialize(
    as = OrganismImpl.class
)
public interface Organism {
  @JsonAnyGetter
  Map<String, Object> getAdditionalProperties();

  @JsonAnySetter
  void setAdditionalProperties(String key, Object value);

  @JsonProperty("organismName")
  String getOrganismName();

  @JsonProperty("organismName")
  void setOrganismName(String organismName);

  @JsonProperty("geneTemplate")
  String getGeneTemplate();

  @JsonProperty("geneTemplate")
  void setGeneTemplate(String geneTemplate);

  @JsonProperty("startingGeneInt")
  long getStartingGeneInt();

  @JsonProperty("startingGeneInt")
  void setStartingGeneInt(long startingGeneInt);

  @JsonProperty("startingTranscriptInt")
  long getStartingTranscriptInt();

  @JsonProperty("startingTranscriptInt")
  void setStartingTranscriptInt(long startingTranscriptInt);
}
