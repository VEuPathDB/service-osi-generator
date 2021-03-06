package org.veupathdb.service.osi.generated.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonTypeName("forbidden")
@JsonDeserialize(
    as = ForbiddenErrorImpl.class
)
public interface ForbiddenError extends Error {
  String _DISCRIMINATOR_TYPE_NAME = "forbidden";

  @JsonProperty("status")
  String getStatus();

  @JsonProperty("message")
  String getMessage();

  @JsonProperty("message")
  void setMessage(String message);
}
