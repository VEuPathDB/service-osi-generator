package org.veupathdb.service.osi.generated.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.EXISTING_PROPERTY,
    property = "status"
)
@JsonSubTypes({
    @JsonSubTypes.Type(org.veupathdb.service.osi.generated.model.ForbiddenError.class),
    @JsonSubTypes.Type(org.veupathdb.service.osi.generated.model.ServerError.class),
    @JsonSubTypes.Type(org.veupathdb.service.osi.generated.model.UnprocessableEntityError.class),
    @JsonSubTypes.Type(org.veupathdb.service.osi.generated.model.NotFoundError.class),
    @JsonSubTypes.Type(org.veupathdb.service.osi.generated.model.UnauthorizedError.class),
    @JsonSubTypes.Type(org.veupathdb.service.osi.generated.model.MethodNotAllowedError.class),
    @JsonSubTypes.Type(org.veupathdb.service.osi.generated.model.BadRequestError.class),
    @JsonSubTypes.Type(org.veupathdb.service.osi.generated.model.Error.class)
})
@JsonDeserialize(
    as = ErrorImpl.class
)
public interface Error {
  String _DISCRIMINATOR_TYPE_NAME = "Error";

  @JsonProperty("status")
  String getStatus();

  @JsonProperty("message")
  String getMessage();

  @JsonProperty("message")
  void setMessage(String message);
}
