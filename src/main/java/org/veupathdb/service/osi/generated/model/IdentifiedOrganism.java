package org.veupathdb.service.osi.generated.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Map;

@JsonDeserialize(
    as = IdentifiedOrganismImpl.class
)
public interface IdentifiedOrganism extends Organism {
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

  @JsonProperty("organismId")
  long getOrganismId();

  @JsonProperty("organismId")
  void setOrganismId(long organismId);
}
