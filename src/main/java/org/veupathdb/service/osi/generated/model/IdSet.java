package org.veupathdb.service.osi.generated.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.List;
import java.util.Map;

@JsonDeserialize(
    as = IdSetImpl.class
)
public interface IdSet {
  @JsonAnyGetter
  Map<String, Object> getAdditionalProperties();

  @JsonAnySetter
  void setAdditionalProperties(String key, Object value);

  @JsonProperty("organism")
  IdentifiedOrganism getOrganism();

  @JsonProperty("organism")
  void setOrganism(IdentifiedOrganism organism);

  @JsonProperty("issuedIds")
  List<TranscriptSet> getIssuedIds();

  @JsonProperty("issuedIds")
  void setIssuedIds(List<TranscriptSet> issuedIds);

  @JsonProperty("created")
  long getCreated();

  @JsonProperty("created")
  void setCreated(long created);
}