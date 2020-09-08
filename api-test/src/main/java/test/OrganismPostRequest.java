package test;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class OrganismPostRequest
{
  public static final String
    KEY_ORG_NAME   = "organismName",
    KEY_TEMPLATE   = "template",
    KEY_GENE_START = "geneIntStart",
    KEY_TRAN_START = "transcriptIntStart";

  private String organismName;
  private String template;
  private Long   geneIntStart;
  private Long   transcriptIntStart;

  @JsonGetter(KEY_ORG_NAME)
  public String getOrganismName() {
    return organismName;
  }

  @JsonSetter(KEY_ORG_NAME)
  public OrganismPostRequest setOrganismName(String organismName) {
    this.organismName = organismName;
    return this;
  }

  @JsonGetter(KEY_TEMPLATE)
  public String getTemplate() {
    return template;
  }

  @JsonSetter(KEY_TEMPLATE)
  public OrganismPostRequest setTemplate(String template) {
    this.template = template;
    return this;
  }

  @JsonGetter(KEY_GENE_START)
  public Long getGeneIntStart() {
    return geneIntStart;
  }

  @JsonSetter(KEY_GENE_START)
  public OrganismPostRequest setGeneIntStart(Long geneIntStart) {
    this.geneIntStart = geneIntStart;
    return this;
  }

  @JsonGetter(KEY_TRAN_START)
  public Long getTranscriptIntStart() {
    return transcriptIntStart;
  }

  @JsonSetter(KEY_TRAN_START)
  public OrganismPostRequest setTranscriptIntStart(Long transcriptIntStart) {
    this.transcriptIntStart = transcriptIntStart;
    return this;
  }
}
