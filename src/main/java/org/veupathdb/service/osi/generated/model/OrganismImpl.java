package org.veupathdb.service.osi.generated.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "organismName",
    "geneTemplate",
    "startingGeneInt",
    "startingTranscriptInt"
})
public class OrganismImpl implements Organism {
  @JsonProperty("organismName")
  private String organismName;

  @JsonProperty("geneTemplate")
  private String geneTemplate;

  @JsonProperty("startingGeneInt")
  private long startingGeneInt;

  @JsonProperty("startingTranscriptInt")
  private long startingTranscriptInt;

  @JsonIgnore
  private Map<String, Object> additionalProperties = new ExcludingMap();

  @JsonProperty("organismName")
  public String getOrganismName() {
    return this.organismName;
  }

  @JsonProperty("organismName")
  public void setOrganismName(String organismName) {
    this.organismName = organismName;
  }

  @JsonProperty("geneTemplate")
  public String getGeneTemplate() {
    return this.geneTemplate;
  }

  @JsonProperty("geneTemplate")
  public void setGeneTemplate(String geneTemplate) {
    this.geneTemplate = geneTemplate;
  }

  @JsonProperty("startingGeneInt")
  public long getStartingGeneInt() {
    return this.startingGeneInt;
  }

  @JsonProperty("startingGeneInt")
  public void setStartingGeneInt(long startingGeneInt) {
    this.startingGeneInt = startingGeneInt;
  }

  @JsonProperty("startingTranscriptInt")
  public long getStartingTranscriptInt() {
    return this.startingTranscriptInt;
  }

  @JsonProperty("startingTranscriptInt")
  public void setStartingTranscriptInt(long startingTranscriptInt) {
    this.startingTranscriptInt = startingTranscriptInt;
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
