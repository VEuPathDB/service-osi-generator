package org.veupathdb.service.osi.generated.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(
    as = OrganismResponseImpl.class
)
public interface OrganismResponse {
  @JsonProperty("organismId")
  long getOrganismId();

  @JsonProperty("organismId")
  void setOrganismId(long organismId);

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

  @JsonProperty("geneIntCurrent")
  long getGeneIntCurrent();

  @JsonProperty("geneIntCurrent")
  void setGeneIntCurrent(long geneIntCurrent);

  @JsonProperty("transcriptIntCurrent")
  long getTranscriptIntCurrent();

  @JsonProperty("transcriptIntCurrent")
  void setTranscriptIntCurrent(long transcriptIntCurrent);

  @JsonProperty("createdOn")
  Date getCreatedOn();

  @JsonProperty("createdOn")
  void setCreatedOn(Date createdOn);

  @JsonProperty("createdBy")
  long getCreatedBy();

  @JsonProperty("createdBy")
  void setCreatedBy(long createdBy);
}
