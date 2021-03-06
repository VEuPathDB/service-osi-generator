package org.veupathdb.service.osi.generated.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(
    as = OrganismPostRequestImpl.class
)
public interface OrganismPostRequest {
  @JsonProperty("organismName")
  String getOrganismName();

  @JsonProperty("organismName")
  void setOrganismName(String organismName);

  @JsonProperty("template")
  String getTemplate();

  @JsonProperty("template")
  void setTemplate(String template);

  @JsonProperty("geneIntStart")
  Long getGeneIntStart();

  @JsonProperty("geneIntStart")
  void setGeneIntStart(Long geneIntStart);

  @JsonProperty(
      value = "transcriptIntStart",
      defaultValue = "1"
  )
  Long getTranscriptIntStart();

  @JsonProperty(
      value = "transcriptIntStart",
      defaultValue = "1"
  )
  void setTranscriptIntStart(Long transcriptIntStart);
}
