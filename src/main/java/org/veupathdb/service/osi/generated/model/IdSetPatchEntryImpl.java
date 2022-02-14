package org.veupathdb.service.osi.generated.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "geneId",
    "transcripts"
})
public class IdSetPatchEntryImpl implements IdSetPatchEntry {
  @JsonProperty("geneId")
  private String geneId;

  @JsonProperty("transcripts")
  private Integer transcripts;

  @JsonProperty("geneId")
  public String getGeneId() {
    return this.geneId;
  }

  @JsonProperty("geneId")
  public void setGeneId(String geneId) {
    this.geneId = geneId;
  }

  @JsonProperty("transcripts")
  public Integer getTranscripts() {
    return this.transcripts;
  }

  @JsonProperty("transcripts")
  public void setTranscripts(Integer transcripts) {
    this.transcripts = transcripts;
  }
}
