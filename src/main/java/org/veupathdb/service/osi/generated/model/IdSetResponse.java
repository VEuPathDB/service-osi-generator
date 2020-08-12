package org.veupathdb.service.osi.generated.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.List;
import java.util.Map;

@JsonDeserialize(
    as = IdSetResponseImpl.class
)
public interface IdSetResponse {
  @JsonAnyGetter
  Map<String, Object> getAdditionalProperties();

  @JsonAnySetter
  void setAdditionalProperties(String key, Object value);

  @JsonProperty("idSetId")
  long getIdSetId();

  @JsonProperty("idSetId")
  void setIdSetId(long idSetId);

  @JsonProperty("template")
  String getTemplate();

  @JsonProperty("template")
  void setTemplate(String template);

  @JsonProperty("geneIntStart")
  long getGeneIntStart();

  @JsonProperty("geneIntStart")
  void setGeneIntStart(long geneIntStart);

  @JsonProperty("generatedGeneCount")
  int getGeneratedGeneCount();

  @JsonProperty("generatedGeneCount")
  void setGeneratedGeneCount(int generatedGeneCount);

  @JsonProperty("generatedIds")
  List<GeneratedTranscriptEntry> getGeneratedIds();

  @JsonProperty("generatedIds")
  void setGeneratedIds(List<GeneratedTranscriptEntry> generatedIds);
}
