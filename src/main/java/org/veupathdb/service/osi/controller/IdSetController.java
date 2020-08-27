package org.veupathdb.service.osi.controller;

import java.util.List;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

import org.veupathdb.service.osi.generated.model.IdSetPatchEntry;
import org.veupathdb.service.osi.generated.model.IdSetPostRequest;
import org.veupathdb.service.osi.generated.resources.IdSets;
import org.veupathdb.service.osi.service.user.UserService;

import static org.veupathdb.service.osi.service.genes.IdSetService.*;

public class IdSetController implements IdSets
{
  private final Request req;

  public IdSetController(@Context Request req) {
    this.req = req;
  }

  @Override
  public GetIdSetsResponse getIdSets(
    Long createdAfter,
    Long createdBefore,
    String createdBy
  ) {
    UserService.requireRequestUser(req);
    return GetIdSetsResponse.respond200WithApplicationJson(
      search(createdAfter, createdBefore, createdBy));
  }

  @Override
  public PostIdSetsResponse postIdSets(IdSetPostRequest entity) {
    return PostIdSetsResponse.respond200WithApplicationJson(
      create(entity, UserService.requireRequestUser(req)));
  }

  @Override
  public GetIdSetsByIdSetIdResponse getIdSetsByIdSetId(int setId) {
    UserService.requireRequestUser(req);
    return GetIdSetsByIdSetIdResponse.respond200WithApplicationJson(get(setId));
  }

  @Override
  public PatchIdSetsByIdSetIdResponse patchIdSetsByIdSetId(
    int setId,
    List < IdSetPatchEntry > entity
  ) {
    return PatchIdSetsByIdSetIdResponse.respond200WithApplicationJson(
      update(entity, UserService.requireRequestUser(req)));
  }
}
