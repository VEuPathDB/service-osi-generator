package org.veupathdb.service.osi.generated.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(
    as = OrganismPutRequestImpl.class
)
public interface OrganismPutRequest {
  @JsonProperty("template")
  String getTemplate();

  @JsonProperty("template")
  void setTemplate(String template);

  @JsonProperty("geneIntStart")
  Long getGeneIntStart();

  @JsonProperty("geneIntStart")
  void setGeneIntStart(Long geneIntStart);

  @JsonProperty("transcriptIntStart")
  Long getTranscriptIntStart();

  @JsonProperty("transcriptIntStart")
  void setTranscriptIntStart(Long transcriptIntStart);
}
