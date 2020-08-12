package org.veupathdb.service.osi.generated.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.List;
import java.util.Map;

@JsonDeserialize(
    as = GeneratedTranscriptEntryImpl.class
)
public interface GeneratedTranscriptEntry {
  @JsonAnyGetter
  Map<String, Object> getAdditionalProperties();

  @JsonAnySetter
  void setAdditionalProperties(String key, Object value);

  @JsonProperty("geneId")
  String getGeneId();

  @JsonProperty("geneId")
  void setGeneId(String geneId);

  @JsonProperty("transcripts")
  List<String> getTranscripts();

  @JsonProperty("transcripts")
  void setTranscripts(List<String> transcripts);

  @JsonProperty("proteins")
  List<String> getProteins();

  @JsonProperty("proteins")
  void setProteins(List<String> proteins);
}
