package org.veupathdb.service.osi.generated.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonTypeName("unauthorized")
@JsonDeserialize(
    as = UnauthorizedErrorImpl.class
)
public interface UnauthorizedError extends Error {
  String _DISCRIMINATOR_TYPE_NAME = "unauthorized";

  @JsonProperty("status")
  String getStatus();

  @JsonProperty("message")
  String getMessage();

  @JsonProperty("message")
  void setMessage(String message);
}
