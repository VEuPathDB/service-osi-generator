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
    "transcripts",
    "proteins"
})
public class GeneratedTranscriptEntryImpl implements GeneratedTranscriptEntry {
  @JsonProperty("geneId")
  private String geneId;

  @JsonProperty("transcripts")
  private List<String> transcripts;

  @JsonProperty("proteins")
  private List<String> proteins;

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

  @JsonProperty("transcripts")
  public List<String> getTranscripts() {
    return this.transcripts;
  }

  @JsonProperty("transcripts")
  public void setTranscripts(List<String> transcripts) {
    this.transcripts = transcripts;
  }

  @JsonProperty("proteins")
  public List<String> getProteins() {
    return this.proteins;
  }

  @JsonProperty("proteins")
  public void setProteins(List<String> proteins) {
    this.proteins = proteins;
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
