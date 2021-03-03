package test;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class IdSetPostRequest
{
  public static final String
    JSON_KEY_ORG_ID    = "organismId",
    JSON_KEY_GEN_GENES = "generateGenes";

  private Long organismId;

  private Integer generateGenes;

  @JsonGetter(JSON_KEY_ORG_ID)
  public Long getOrganismId() {
    return organismId;
  }

  @JsonSetter(JSON_KEY_ORG_ID)
  public IdSetPostRequest setOrganismId(Long organismId) {
    this.organismId = organismId;
    return this;
  }

  @JsonGetter(JSON_KEY_GEN_GENES)
  public Integer getGenerateGenes() {
    return generateGenes;
  }

  @JsonSetter(JSON_KEY_GEN_GENES)
  public IdSetPostRequest setGenerateGenes(Integer generateGenes) {
    this.generateGenes = generateGenes;
    return this;
  }
}
