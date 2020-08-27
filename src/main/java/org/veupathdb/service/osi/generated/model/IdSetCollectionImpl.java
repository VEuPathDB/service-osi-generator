package org.veupathdb.service.osi.generated.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "collectionId",
    "name",
    "createdBy",
    "createdOn",
    "idSets"
})
public class IdSetCollectionImpl implements IdSetCollection {
  @JsonProperty("collectionId")
  private int collectionId;

  @JsonProperty("name")
  private String name;

  @JsonProperty("createdBy")
  private int createdBy;

  @JsonProperty("createdOn")
  @JsonFormat(
      shape = JsonFormat.Shape.STRING,
      pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"
  )
  @JsonDeserialize(
      using = TimestampDeserializer.class
  )
  private Date createdOn;

  @JsonProperty("idSets")
  private List<IdSetResponse> idSets;

  @JsonProperty("collectionId")
  public int getCollectionId() {
    return this.collectionId;
  }

  @JsonProperty("collectionId")
  public void setCollectionId(int collectionId) {
    this.collectionId = collectionId;
  }

  @JsonProperty("name")
  public String getName() {
    return this.name;
  }

  @JsonProperty("name")
  public void setName(String name) {
    this.name = name;
  }

  @JsonProperty("createdBy")
  public int getCreatedBy() {
    return this.createdBy;
  }

  @JsonProperty("createdBy")
  public void setCreatedBy(int createdBy) {
    this.createdBy = createdBy;
  }

  @JsonProperty("createdOn")
  public Date getCreatedOn() {
    return this.createdOn;
  }

  @JsonProperty("createdOn")
  public void setCreatedOn(Date createdOn) {
    this.createdOn = createdOn;
  }

  @JsonProperty("idSets")
  public List<IdSetResponse> getIdSets() {
    return this.idSets;
  }

  @JsonProperty("idSets")
  public void setIdSets(List<IdSetResponse> idSets) {
    this.idSets = idSets;
  }
}
