package test;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class IdSetResponse
{
  public static final String
    JSON_KEY_ID_SET_ID            = "idSetId",
    JSON_KEY_COLLECTION_ID        = "collectionId",
    JSON_KEY_TEMPLATE             = "template",
    JSON_KEY_GENE_INT_START       = "geneIntStart",
    JSON_KEY_GENERATED_GENE_COUNT = "generatedGeneCount",
    JSON_KEY_GENERATED_IDS        = "generatedIds",
    JSON_KEY_CREATED_ON           = "createdOn",
    JSON_KEY_CREATED_BY           = "createdBy";

  private Long idSetId;

  private Long collectionId;

  private String template;

  private Long geneIntStart;

  private Integer generatedGeneCount;

  private GeneratedTranscriptEntry[] generatedIds;

  private OffsetDateTime createdOn;

  private Long createdBy;

  @JsonGetter(JSON_KEY_ID_SET_ID)
  public Long getIdSetId() {
    return idSetId;
  }

  @JsonSetter(JSON_KEY_ID_SET_ID)
  public IdSetResponse setIdSetId(Long idSetId) {
    this.idSetId = idSetId;
    return this;
  }

  @JsonGetter(JSON_KEY_COLLECTION_ID)
  public Long getCollectionId() {
    return collectionId;
  }

  @JsonSetter(JSON_KEY_COLLECTION_ID)
  public IdSetResponse setCollectionId(Long collectionId) {
    this.collectionId = collectionId;
    return this;
  }

  @JsonGetter(JSON_KEY_TEMPLATE)
  public String getTemplate() {
    return template;
  }

  @JsonSetter(JSON_KEY_TEMPLATE)
  public IdSetResponse setTemplate(String template) {
    this.template = template;
    return this;
  }

  @JsonGetter(JSON_KEY_GENE_INT_START)
  public Long getGeneIntStart() {
    return geneIntStart;
  }

  @JsonSetter(JSON_KEY_GENE_INT_START)
  public IdSetResponse setGeneIntStart(Long geneIntStart) {
    this.geneIntStart = geneIntStart;
    return this;
  }

  @JsonGetter(JSON_KEY_GENERATED_GENE_COUNT)
  public Integer getGeneratedGeneCount() {
    return generatedGeneCount;
  }

  @JsonSetter(JSON_KEY_GENERATED_GENE_COUNT)
  public IdSetResponse setGeneratedGeneCount(Integer generatedGeneCount) {
    this.generatedGeneCount = generatedGeneCount;
    return this;
  }

  @JsonGetter(JSON_KEY_GENERATED_IDS)
  public GeneratedTranscriptEntry[] getGeneratedIds() {
    return generatedIds;
  }

  @JsonSetter(JSON_KEY_GENERATED_IDS)
  public IdSetResponse setGeneratedIds(GeneratedTranscriptEntry[] generatedIds) {
    this.generatedIds = generatedIds;
    return this;
  }

  @JsonGetter(JSON_KEY_CREATED_ON)
  public OffsetDateTime getCreatedOn() {
    return createdOn;
  }

  @JsonSetter(JSON_KEY_CREATED_ON)
  public IdSetResponse setCreatedOn(OffsetDateTime createdOn) {
    this.createdOn = createdOn;
    return this;
  }

  @JsonGetter(JSON_KEY_CREATED_BY)
  public Long getCreatedBy() {
    return createdBy;
  }

  @JsonSetter(JSON_KEY_CREATED_BY)
  public IdSetResponse setCreatedBy(Long createdBy) {
    this.createdBy = createdBy;
    return this;
  }
}
