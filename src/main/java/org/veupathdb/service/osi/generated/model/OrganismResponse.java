package org.veupathdb.service.osi.generated.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Map;

@JsonDeserialize(
    as = OrganismResponseImpl.class
)
public interface OrganismResponse extends OrganismRequest {
  @JsonAnyGetter
  Map<String, Object> getAdditionalProperties();

  @JsonAnySetter
  void setAdditionalProperties(String key, Object value);

  @JsonProperty("organismName")
  String getOrganismName();

  @JsonProperty("organismName")
  void setOrganismName(String organismName);

  @JsonProperty("template")
  String getTemplate();

  @JsonProperty("template")
  void setTemplate(String template);

  @JsonProperty("geneIntStart")
  long getGeneIntStart();

  @JsonProperty("geneIntStart")
  void setGeneIntStart(long geneIntStart);

  @JsonProperty("transcriptIntStart")
  long getTranscriptIntStart();

  @JsonProperty("transcriptIntStart")
  void setTranscriptIntStart(long transcriptIntStart);

  @JsonProperty("organismId")
  int getOrganismId();

  @JsonProperty("organismId")
  void setOrganismId(int organismId);

  @JsonProperty("geneIntCurrent")
  long getGeneIntCurrent();

  @JsonProperty("geneIntCurrent")
  void setGeneIntCurrent(long geneIntCurrent);

  @JsonProperty("transcriptIntCurrent")
  long getTranscriptIntCurrent();

  @JsonProperty("transcriptIntCurrent")
  void setTranscriptIntCurrent(long transcriptIntCurrent);
}
