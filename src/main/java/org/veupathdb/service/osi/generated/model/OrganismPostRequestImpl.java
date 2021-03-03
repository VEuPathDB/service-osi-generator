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
public class OrganismPostRequestImpl implements OrganismPostRequest {
  @JsonProperty("organismName")
  private String organismName;

  @JsonProperty("template")
  private String template;

  @JsonProperty("geneIntStart")
  private Long geneIntStart;

  @JsonProperty(
      value = "transcriptIntStart",
      defaultValue = "1"
  )
  private Long transcriptIntStart;

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
  public Long getGeneIntStart() {
    return this.geneIntStart;
  }

  @JsonProperty("geneIntStart")
  public void setGeneIntStart(Long geneIntStart) {
    this.geneIntStart = geneIntStart;
  }

  @JsonProperty(
      value = "transcriptIntStart",
      defaultValue = "1"
  )
  public Long getTranscriptIntStart() {
    return this.transcriptIntStart;
  }

  @JsonProperty(
      value = "transcriptIntStart",
      defaultValue = "1"
  )
  public void setTranscriptIntStart(Long transcriptIntStart) {
    this.transcriptIntStart = transcriptIntStart;
  }
}
