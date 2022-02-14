package org.veupathdb.service.osi.generated.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Date;
import java.util.List;

@JsonDeserialize(
    as = IdSetResponseImpl.class
)
public interface IdSetResponse {
  @JsonProperty("idSetId")
  Long getIdSetId();

  @JsonProperty("idSetId")
  void setIdSetId(Long idSetId);

  @JsonProperty("organismId")
  Long getOrganismId();

  @JsonProperty("organismId")
  void setOrganismId(Long organismId);

  @JsonProperty("template")
  String getTemplate();

  @JsonProperty("template")
  void setTemplate(String template);

  @JsonProperty("geneIntStart")
  Long getGeneIntStart();

  @JsonProperty("geneIntStart")
  void setGeneIntStart(Long geneIntStart);

  @JsonProperty("generatedGeneCount")
  Integer getGeneratedGeneCount();

  @JsonProperty("generatedGeneCount")
  void setGeneratedGeneCount(Integer generatedGeneCount);

  @JsonProperty("generatedIds")
  List<GeneratedTranscriptEntry> getGeneratedIds();

  @JsonProperty("generatedIds")
  void setGeneratedIds(List<GeneratedTranscriptEntry> generatedIds);

  @JsonProperty("createdOn")
  Date getCreatedOn();

  @JsonProperty("createdOn")
  void setCreatedOn(Date createdOn);

  @JsonProperty("createdBy")
  Long getCreatedBy();

  @JsonProperty("createdBy")
  void setCreatedBy(Long createdBy);
}
