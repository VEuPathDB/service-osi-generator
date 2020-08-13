package org.veupathdb.service.osi.generated.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "organismName",
    "template",
    "geneIntStart",
    "transcriptIntStart"
})
public class OrganismRequestImpl implements OrganismRequest {
  @JsonProperty("organismName")
  private String organismName;

  @JsonProperty("template")
  private String template;

  @JsonProperty("geneIntStart")
  private long geneIntStart;

  @JsonProperty("transcriptIntStart")
  private long transcriptIntStart;

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
}
