package test;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class GeneratedTranscriptEntry
{
  public static final String
    JSON_KEY_GENE_ID     = "geneId",
    JSON_KEY_TRANSCRIPTS = "transcripts",
    JSON_KEY_PROTEINS    = "proteins";

  private String geneId;

  private String[] transcripts;

  private String[] proteins;

  @JsonGetter(JSON_KEY_GENE_ID)
  public String getGeneId() {
    return geneId;
  }

  @JsonSetter(JSON_KEY_GENE_ID)
  public GeneratedTranscriptEntry setGeneId(String geneId) {
    this.geneId = geneId;
    return this;
  }

  @JsonGetter(JSON_KEY_TRANSCRIPTS)
  public String[] getTranscripts() {
    return transcripts;
  }

  @JsonSetter(JSON_KEY_TRANSCRIPTS)
  public GeneratedTranscriptEntry setTranscripts(String[] transcripts) {
    this.transcripts = transcripts;
    return this;
  }

  @JsonGetter(JSON_KEY_PROTEINS)
  public String[] getProteins() {
    return proteins;
  }

  @JsonSetter(JSON_KEY_PROTEINS)
  public GeneratedTranscriptEntry setProteins(String[] proteins) {
    this.proteins = proteins;
    return this;
  }
}
