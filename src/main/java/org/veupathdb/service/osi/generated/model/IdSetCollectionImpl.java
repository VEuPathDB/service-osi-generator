package org.veupathdb.service.osi.generated.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "collectionId",
    "name",
    "createdBy",
    "created",
    "idSets"
})
public class IdSetCollectionImpl implements IdSetCollection {
  @JsonProperty("collectionId")
  private int collectionId;

  @JsonProperty("name")
  private String name;

  @JsonProperty("createdBy")
  private String createdBy;

  @JsonProperty("created")
  private long created;

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
  public String getCreatedBy() {
    return this.createdBy;
  }

  @JsonProperty("createdBy")
  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  @JsonProperty("created")
  public long getCreated() {
    return this.created;
  }

  @JsonProperty("created")
  public void setCreated(long created) {
    this.created = created;
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
