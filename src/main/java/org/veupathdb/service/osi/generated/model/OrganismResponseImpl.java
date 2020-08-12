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
    "template",
    "geneIntStart",
    "transcriptIntStart",
    "organismId",
    "geneIntCurrent",
    "transcriptIntCurrent"
})
public class OrganismResponseImpl implements OrganismResponse {
  @JsonProperty("organismName")
  private String organismName;

  @JsonProperty("template")
  private String template;

  @JsonProperty("geneIntStart")
  private long geneIntStart;

  @JsonProperty("transcriptIntStart")
  private long transcriptIntStart;

  @JsonProperty("organismId")
  private int organismId;

  @JsonProperty("geneIntCurrent")
  private long geneIntCurrent;

  @JsonProperty("transcriptIntCurrent")
  private long transcriptIntCurrent;

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

  @JsonProperty("template")
  public String getTemplate() {
    return this.template;
  }

  @JsonProperty("template")
  public void setTemplate(String template) {
    this.template = template;
  }

  @JsonProperty("geneIntStart")
  public long getGeneIntStart() {
    return this.geneIntStart;
  }

  @JsonProperty("geneIntStart")
  public void setGeneIntStart(long geneIntStart) {
    this.geneIntStart = geneIntStart;
  }

  @JsonProperty("transcriptIntStart")
  public long getTranscriptIntStart() {
    return this.transcriptIntStart;
  }

  @JsonProperty("transcriptIntStart")
  public void setTranscriptIntStart(long transcriptIntStart) {
    this.transcriptIntStart = transcriptIntStart;
  }

  @JsonProperty("organismId")
  public int getOrganismId() {
    return this.organismId;
  }

  @JsonProperty("organismId")
  public void setOrganismId(int organismId) {
    this.organismId = organismId;
  }

  @JsonProperty("geneIntCurrent")
  public long getGeneIntCurrent() {
    return this.geneIntCurrent;
  }

  @JsonProperty("geneIntCurrent")
  public void setGeneIntCurrent(long geneIntCurrent) {
    this.geneIntCurrent = geneIntCurrent;
  }

  @JsonProperty("transcriptIntCurrent")
  public long getTranscriptIntCurrent() {
    return this.transcriptIntCurrent;
  }

  @JsonProperty("transcriptIntCurrent")
  public void setTranscriptIntCurrent(long transcriptIntCurrent) {
    this.transcriptIntCurrent = transcriptIntCurrent;
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
