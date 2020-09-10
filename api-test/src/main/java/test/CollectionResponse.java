package test;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class CollectionResponse
{
  public static final String
    JSON_KEY_COLLECTION_ID = "collectionId",
    JSON_KEY_NAME          = "name",
    JSON_KEY_CREATED_ON    = "createdOn",
    JSON_KEY_CREATED_BY    = "createdBy",
    JSON_KEY_ID_SETS       = "idSets";

  private Long collectionId;

  private String name;

  private long createdBy;

  private OffsetDateTime createdOn;

  private IdSetResponse[] idSets;

  @JsonGetter(JSON_KEY_COLLECTION_ID)
  public Long getCollectionId() {
    return collectionId;
  }

  @JsonSetter(JSON_KEY_COLLECTION_ID)
  public CollectionResponse setCollectionId(Long collectionId) {
    this.collectionId = collectionId;
    return this;
  }

  @JsonGetter(JSON_KEY_NAME)
  public String getName() {
    return name;
  }

  @JsonSetter(JSON_KEY_NAME)
  public CollectionResponse setName(String name) {
    this.name = name;
    return this;
  }

  @JsonGetter(JSON_KEY_CREATED_BY)
  public long getCreatedBy() {
    return createdBy;
  }

  @JsonSetter(JSON_KEY_CREATED_BY)
  public CollectionResponse setCreatedBy(long createdBy) {
    this.createdBy = createdBy;
    return this;
  }

  @JsonGetter(JSON_KEY_CREATED_ON)
  public OffsetDateTime getCreatedOn() {
    return createdOn;
  }

  @JsonSetter(JSON_KEY_CREATED_ON)
  public CollectionResponse setCreatedOn(OffsetDateTime createdOn) {
    this.createdOn = createdOn;
    return this;
  }

  @JsonGetter(JSON_KEY_ID_SETS)
  public IdSetResponse[] getIdSets() {
    return idSets;
  }

  @JsonSetter(JSON_KEY_ID_SETS)
  public CollectionResponse setIdSets(IdSetResponse[] idSets) {
    this.idSets = idSets;
    return this;
  }
}
