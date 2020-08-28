package org.veupathdb.service.osi.generated.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(
    as = GeneratedTranscriptEntryImpl.class
)
public interface GeneratedTranscriptEntry {
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
