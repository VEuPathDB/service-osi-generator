package org.veupathdb.service.osi.generated.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.List;
import java.util.Map;

@JsonDeserialize(
    as = IdSetCollectionImpl.class
)
public interface IdSetCollection {
  @JsonAnyGetter
  Map<String, Object> getAdditionalProperties();

  @JsonAnySetter
  void setAdditionalProperties(String key, Object value);

  @JsonProperty("collectionId")
  int getCollectionId();

  @JsonProperty("collectionId")
  void setCollectionId(int collectionId);

  @JsonProperty("name")
  String getName();

  @JsonProperty("name")
  void setName(String name);

  @JsonProperty("createdBy")
  String getCreatedBy();

  @JsonProperty("createdBy")
  void setCreatedBy(String createdBy);

  @JsonProperty("created")
  long getCreated();

  @JsonProperty("created")
  void setCreated(long created);

  @JsonProperty("idSets")
  List<IdSetResponse> getIdSets();

  @JsonProperty("idSets")
  void setIdSets(List<IdSetResponse> idSets);
}
