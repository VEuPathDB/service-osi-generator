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
    "issuedIds",
    "created"
})
public class IdSetImpl implements IdSet {
  @JsonProperty("organism")
  private IdentifiedOrganism organism;

  @JsonProperty("issuedIds")
  private List<TranscriptSet> issuedIds;

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

  @JsonProperty("issuedIds")
  public List<TranscriptSet> getIssuedIds() {
    return this.issuedIds;
  }

  @JsonProperty("issuedIds")
  public void setIssuedIds(List<TranscriptSet> issuedIds) {
    this.issuedIds = issuedIds;
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
