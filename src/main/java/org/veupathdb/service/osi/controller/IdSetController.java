package org.veupathdb.service.osi.controller;

import java.util.List;

import org.veupathdb.service.osi.generated.model.IdSetPatchEntry;
import org.veupathdb.service.osi.generated.model.IdSetPostRequest;
import org.veupathdb.service.osi.generated.resources.IdSets;

public class IdSetController implements IdSets
{
  @Override
  public GetIdSetsResponse getIdSets(
    Long createdAfter,
    Long createdBefore,
    String createdBy
  ) {


    return null;
  }

  @Override
  public PostIdSetsResponse postIdSets(IdSetPostRequest entity) {
    return null;
  }

  @Override
  public GetIdSetsByGeneSetIdResponse getIdSetsByGeneSetId(String geneSetId) {
    return null;
  }

  @Override
  public PatchIdSetsByGeneSetIdResponse patchIdSetsByGeneSetId(
    String geneSetId, List < IdSetPatchEntry > entity
  ) {
    return null;
  }
}
