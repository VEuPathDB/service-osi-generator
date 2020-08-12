package org.veupathdb.service.osi.generated.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.List;
import java.util.Map;

@JsonDeserialize(
    as = TranscriptSetImpl.class
)
public interface TranscriptSet {
  @JsonAnyGetter
  Map<String, Object> getAdditionalProperties();

  @JsonAnySetter
  void setAdditionalProperties(String key, Object value);

  @JsonProperty("geneId")
  String getGeneId();

  @JsonProperty("geneId")
  void setGeneId(String geneId);

  @JsonProperty("transcriptIds")
  List<String> getTranscriptIds();

  @JsonProperty("transcriptIds")
  void setTranscriptIds(List<String> transcriptIds);
}
