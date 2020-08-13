package org.veupathdb.service.osi.generated.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "organismId",
    "organismName",
    "template",
    "geneIntStart",
    "transcriptIntStart",
    "geneIntCurrent",
    "transcriptIntCurrent"
})
public class OrganismResponseImpl implements OrganismResponse {
  @JsonProperty("organismId")
  private int organismId;

  @JsonProperty("organismName")
  private String organismName;

  @JsonProperty("template")
  private String template;

  @JsonProperty("geneIntStart")
  private long geneIntStart;

  @JsonProperty("transcriptIntStart")
  private long transcriptIntStart;

  @JsonProperty("geneIntCurrent")
  private long geneIntCurrent;

  @JsonProperty("transcriptIntCurrent")
  private long transcriptIntCurrent;

  @JsonProperty("organismId")
  public int getOrganismId() {
    return this.organismId;
  }

  @JsonProperty("organismId")
  public void setOrganismId(int organismId) {
    this.organismId = organismId;
  }

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
}
