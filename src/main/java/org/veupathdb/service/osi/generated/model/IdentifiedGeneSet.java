package org.veupathdb.service.osi.generated.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.List;
import java.util.Map;

@JsonDeserialize(
    as = IdentifiedGeneSetImpl.class
)
public interface IdentifiedGeneSet extends GeneSet {
  @JsonAnyGetter
  Map<String, Object> getAdditionalProperties();

  @JsonAnySetter
  void setAdditionalProperties(String key, Object value);

  @JsonProperty("organsim")
  IdentifiedOrganism getOrgansim();

  @JsonProperty("organsim")
  void setOrgansim(IdentifiedOrganism organsim);

  @JsonProperty("template")
  String getTemplate();

  @JsonProperty("template")
  void setTemplate(String template);

  @JsonProperty("startingGeneInt")
  long getStartingGeneInt();

  @JsonProperty("startingGeneInt")
  void setStartingGeneInt(long startingGeneInt);

  @JsonProperty("geneIds")
  long getGeneIds();

  @JsonProperty("geneIds")
  void setGeneIds(long geneIds);

  @JsonProperty("transcriptIds")
  List<String> getTranscriptIds();

  @JsonProperty("transcriptIds")
  void setTranscriptIds(List<String> transcriptIds);

  @JsonProperty("created")
  long getCreated();

  @JsonProperty("created")
  void setCreated(long created);

  @JsonProperty("geneSetId")
  long getGeneSetId();

  @JsonProperty("geneSetId")
  void setGeneSetId(long geneSetId);
}
