package org.veupathdb.service.osi.generated.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Date;

@JsonDeserialize(
    as = OrganismResponseImpl.class
)
public interface OrganismResponse {
  @JsonProperty("organismId")
  Long getOrganismId();

  @JsonProperty("organismId")
  void setOrganismId(Long organismId);

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

  @JsonProperty("transcriptIntStart")
  Long getTranscriptIntStart();

  @JsonProperty("transcriptIntStart")
  void setTranscriptIntStart(Long transcriptIntStart);

  @JsonProperty("geneIntCurrent")
  Long getGeneIntCurrent();

  @JsonProperty("geneIntCurrent")
  void setGeneIntCurrent(Long geneIntCurrent);

  @JsonProperty("transcriptIntCurrent")
  Long getTranscriptIntCurrent();

  @JsonProperty("transcriptIntCurrent")
  void setTranscriptIntCurrent(Long transcriptIntCurrent);

  @JsonProperty("createdOn")
  Date getCreatedOn();

  @JsonProperty("createdOn")
  void setCreatedOn(Date createdOn);

  @JsonProperty("createdBy")
  Long getCreatedBy();

  @JsonProperty("createdBy")
  void setCreatedBy(Long createdBy);
}
