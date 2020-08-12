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
    "organism",
    "template",
    "startingGeneInt",
    "geneIds",
    "transcriptIds",
    "created"
})
public class IdSetImpl implements IdSet {
  @JsonProperty("organism")
  private IdentifiedOrganism organism;

  @JsonProperty("template")
  private String template;

  @JsonProperty("startingGeneInt")
  private long startingGeneInt;

  @JsonProperty("geneIds")
  private long geneIds;

  @JsonProperty("transcriptIds")
  private List<String> transcriptIds;

  @JsonProperty("created")
  private long created;

  @JsonIgnore
  private Map<String, Object> additionalProperties = new ExcludingMap();

  @JsonProperty("organism")
  public IdentifiedOrganism getOrganism() {
    return this.organism;
  }

  @JsonProperty("organism")
  public void setOrganism(IdentifiedOrganism organism) {
    this.organism = organism;
  }

  @JsonProperty("template")
  public String getTemplate() {
    return this.template;
  }

  @JsonProperty("template")
  public void setTemplate(String template) {
    this.template = template;
  }

  @JsonProperty("startingGeneInt")
  public long getStartingGeneInt() {
    return this.startingGeneInt;
  }

  @JsonProperty("startingGeneInt")
  public void setStartingGeneInt(long startingGeneInt) {
    this.startingGeneInt = startingGeneInt;
  }

  @JsonProperty("geneIds")
  public long getGeneIds() {
    return this.geneIds;
  }

  @JsonProperty("geneIds")
  public void setGeneIds(long geneIds) {
    this.geneIds = geneIds;
  }

  @JsonProperty("transcriptIds")
  public List<String> getTranscriptIds() {
    return this.transcriptIds;
  }

  @JsonProperty("transcriptIds")
  public void setTranscriptIds(List<String> transcriptIds) {
    this.transcriptIds = transcriptIds;
  }

  @JsonProperty("created")
  public long getCreated() {
    return this.created;
  }

  @JsonProperty("created")
  public void setCreated(long created) {
    this.created = created;
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
