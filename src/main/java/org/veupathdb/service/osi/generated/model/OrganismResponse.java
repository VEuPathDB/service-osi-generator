package org.veupathdb.service.osi.generated.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Date;

@JsonDeserialize(
    as = OrganismResponseImpl.class
)
public interface OrganismResponse {
  @JsonProperty("organismId")
  int getOrganismId();

  @JsonProperty("organismId")
  void setOrganismId(int organismId);

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
  int getCreatedBy();

  @JsonProperty("createdBy")
  void setCreatedBy(int createdBy);
}
