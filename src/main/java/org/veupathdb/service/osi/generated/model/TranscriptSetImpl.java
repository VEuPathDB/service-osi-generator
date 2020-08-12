package org.veupathdb.service.osi.generated.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "geneId",
    "transcriptIds"
})
public class TranscriptSetImpl implements TranscriptSet {
  @JsonProperty("geneId")
  private String geneId;

  @JsonProperty("transcriptIds")
  private List<String> transcriptIds;

  @JsonIgnore
  private Map<String, Object> additionalProperties = new ExcludingMap();

  @JsonProperty("geneId")
  public String getGeneId() {
    return this.geneId;
  }

  @JsonProperty("geneId")
  public void setGeneId(String geneId) {
    this.geneId = geneId;
  }

  @JsonProperty("transcriptIds")
  public List<String> getTranscriptIds() {
    return this.transcriptIds;
  }

  @JsonProperty("transcriptIds")
  public void setTranscriptIds(List<String> transcriptIds) {
    this.transcriptIds = transcriptIds;
  }

  @JsonAnyGetter
  public Map<String, Object> getAdditionalProperties() {
    return additionalProperties;
  }

  @JsonAnySetter
  public void setAdditionalProperties(String key, Object value) {
    this.additionalProperties.put(key, value);
  }
}
