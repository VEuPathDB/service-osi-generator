package org.veupathdb.service.osi.generated.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(
    as = CollectionResponseImpl.class
)
public interface CollectionResponse {
  @JsonProperty("collectionId")
  long getCollectionId();

  @JsonProperty("collectionId")
  void setCollectionId(long collectionId);

  @JsonProperty("name")
  String getName();

  @JsonProperty("name")
  void setName(String name);

  @JsonProperty("createdBy")
  long getCreatedBy();

  @JsonProperty("createdBy")
  void setCreatedBy(long createdBy);

  @JsonProperty("createdOn")
  Date getCreatedOn();

  @JsonProperty("createdOn")
  void setCreatedOn(Date createdOn);

  @JsonProperty("idSets")
  List<IdSetResponse> getIdSets();

  @JsonProperty("idSets")
  void setIdSets(List<IdSetResponse> idSets);
}
