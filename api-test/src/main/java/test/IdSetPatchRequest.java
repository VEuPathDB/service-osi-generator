package test;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class IdSetPatchRequest extends ArrayList < IdSetPatchRequest.Entry >
{
  public static class Entry
  {
    public static final String
      JSON_KEY_GENE_ID     = "geneId",
      JSON_KEY_TRANSCRIPTS = "transcripts";

    private String geneId;

    private Integer transcripts;

    @JsonGetter(JSON_KEY_GENE_ID)
    public String getGeneId() {
      return geneId;
    }

    @JsonSetter(JSON_KEY_GENE_ID)
    public Entry setGeneId(String geneId) {
      this.geneId = geneId;
      return this;
    }

    @JsonGetter(JSON_KEY_TRANSCRIPTS)
    public Integer getTranscripts() {
      return transcripts;
    }

    @JsonSetter(JSON_KEY_TRANSCRIPTS)
    public Entry setTranscripts(Integer transcripts) {
      this.transcripts = transcripts;
      return this;
    }
  }
}
