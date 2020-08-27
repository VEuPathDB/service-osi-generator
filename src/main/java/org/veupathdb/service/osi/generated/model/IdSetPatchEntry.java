package org.veupathdb.service.osi.generated.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(
    as = IdSetPatchEntryImpl.class
)
public interface IdSetPatchEntry {
  @JsonProperty("geneId")
  String getGeneId();

  @JsonProperty("geneId")
  void setGeneId(String geneId);

  @JsonProperty("transcripts")
  long getTranscripts();

  @JsonProperty("transcripts")
  void setTranscripts(long transcripts);
}
