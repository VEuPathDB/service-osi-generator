package org.veupathdb.service.osi.generated.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(
    as = OrganismRequestImpl.class
)
public interface OrganismRequest {
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
}
