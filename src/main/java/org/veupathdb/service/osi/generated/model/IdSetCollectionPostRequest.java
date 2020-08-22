package org.veupathdb.service.osi.generated.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(
    as = IdSetCollectionPostRequestImpl.class
)
public interface IdSetCollectionPostRequest {
  @JsonProperty("name")
  String getName();

  @JsonProperty("name")
  void setName(String name);
}
