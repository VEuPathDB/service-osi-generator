package org.veupathdb.service.osi.generated.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "idSetId",
    "template",
    "geneIntStart",
    "generatedGeneCount",
    "generatedIds",
    "created"
})
public class IdSetResponseImpl implements IdSetResponse {
  @JsonProperty("idSetId")
  private long idSetId;

  @JsonProperty("template")
  private String template;

  @JsonProperty("geneIntStart")
  private long geneIntStart;

  @JsonProperty("generatedGeneCount")
  private int generatedGeneCount;

  @JsonProperty("generatedIds")
  private List<GeneratedTranscriptEntry> generatedIds;

  @JsonProperty("created")
  private long created;

  @JsonProperty("idSetId")
  public long getIdSetId() {
    return this.idSetId;
  }

  @JsonProperty("idSetId")
  public void setIdSetId(long idSetId) {
    this.idSetId = idSetId;
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

  @JsonProperty("generatedGeneCount")
  public int getGeneratedGeneCount() {
    return this.generatedGeneCount;
  }

  @JsonProperty("generatedGeneCount")
  public void setGeneratedGeneCount(int generatedGeneCount) {
    this.generatedGeneCount = generatedGeneCount;
  }

  @JsonProperty("generatedIds")
  public List<GeneratedTranscriptEntry> getGeneratedIds() {
    return this.generatedIds;
  }

  @JsonProperty("generatedIds")
  public void setGeneratedIds(List<GeneratedTranscriptEntry> generatedIds) {
    this.generatedIds = generatedIds;
  }

  @JsonProperty("created")
  public long getCreated() {
    return this.created;
  }

  @JsonProperty("created")
  public void setCreated(long created) {
    this.created = created;
  }
}
