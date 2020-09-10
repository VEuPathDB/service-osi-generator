package test;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrganismPutRequest
{
  public static final String
    KEY_TEMPLATE   = "template",
    KEY_GENE_START = "geneIntStart",
    KEY_TRAN_START = "transcriptIntStart";

  private String template;
  private Long   geneIntStart;
  private Long   transcriptIntStart;

  @JsonGetter(KEY_TEMPLATE)
  public String getTemplate() {
    return template;
  }

  @JsonSetter(KEY_TEMPLATE)
  public OrganismPutRequest setTemplate(String template) {
    this.template = template;
    return this;
  }

  @JsonGetter(KEY_GENE_START)
  public Long getGeneIntStart() {
    return geneIntStart;
  }

  @JsonSetter(KEY_GENE_START)
  public OrganismPutRequest setGeneIntStart(Long geneIntStart) {
    this.geneIntStart = geneIntStart;
    return this;
  }

  @JsonGetter(KEY_TRAN_START)
  public Long getTranscriptIntStart() {
    return transcriptIntStart;
  }

  @JsonSetter(KEY_TRAN_START)
  public OrganismPutRequest setTranscriptIntStart(Long transcriptIntStart) {
    this.transcriptIntStart = transcriptIntStart;
    return this;
  }
}
