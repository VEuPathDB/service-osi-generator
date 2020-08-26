package org.veupathdb.service.osi.generated.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "template",
    "geneIntStart",
    "transcriptIntStart"
})
public class OrganismPutRequestImpl implements OrganismPutRequest {
  @JsonProperty("template")
  private String template;

  @JsonProperty("geneIntStart")
  private Long geneIntStart;

  @JsonProperty("transcriptIntStart")
  private Long transcriptIntStart;

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

  @JsonProperty("transcriptIntStart")
  public Long getTranscriptIntStart() {
    return this.transcriptIntStart;
  }

  @JsonProperty("transcriptIntStart")
  public void setTranscriptIntStart(Long transcriptIntStart) {
    this.transcriptIntStart = transcriptIntStart;
  }
}
