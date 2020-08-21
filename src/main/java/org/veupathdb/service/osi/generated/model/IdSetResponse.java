package org.veupathdb.service.osi.generated.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.List;

@JsonDeserialize(
    as = IdSetResponseImpl.class
)
public interface IdSetResponse {
  @JsonProperty("idSetId")
  long getIdSetId();

  @JsonProperty("idSetId")
  void setIdSetId(long idSetId);

  @JsonProperty("collectionId")
  int getCollectionId();

  @JsonProperty("collectionId")
  void setCollectionId(int collectionId);

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

  @JsonProperty("created")
  long getCreated();

  @JsonProperty("created")
  void setCreated(long created);
}
