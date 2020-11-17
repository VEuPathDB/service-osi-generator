package org.veupathdb.service.osi.controller;

import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

import org.apache.logging.log4j.Logger;
import org.veupathdb.lib.container.jaxrs.providers.LogProvider;
import org.veupathdb.service.osi.generated.model.IdSetPatchEntry;
import org.veupathdb.service.osi.generated.model.IdSetPostRequest;
import org.veupathdb.service.osi.generated.resources.IdSets;

import static org.veupathdb.service.osi.service.idset.IdSetService.*;

public class IdSetController implements IdSets
{
  private final Logger log = LogProvider.logger(getClass());

  private final Request req;

  public IdSetController(@Context Request req) {
    this.req = req;
  }

  @Override
  public GetIdSetsResponse getIdSets(
    final Long createdAfter,
    final Long createdBefore,
    final String createdBy
  ) {
    log.trace("IdSetController#getIdSets(Long, Long, String)");
    return GetIdSetsResponse.respond200WithApplicationJson(
      search(createdAfter, createdBefore, createdBy, req));
  }

  @Override
  public PostIdSetsResponse postIdSets(final IdSetPostRequest entity) {
    log.trace("IdSetController#postIdSets(IdSetPostRequest)");
    return PostIdSetsResponse.respond200WithApplicationJson(
      create(entity, req));
  }

  @Override
  public GetIdSetsByIdSetIdResponse getIdSetsByIdSetId(final Long setId) {
    log.trace("IdSetController#getIdSetsByIdSetId(Long)");
    return GetIdSetsByIdSetIdResponse.respond200WithApplicationJson(
      get(setId, req));
  }

  @Override
  public PatchIdSetsByIdSetIdResponse patchIdSetsByIdSetId(
    final Long setId,
    final List<IdSetPatchEntry> entity
  ) {
    log.trace("IdSetController#patchIdSetsByIdSetId(Long, List)");
    return PatchIdSetsByIdSetIdResponse.respond200WithApplicationJson(
      update(setId, entity, req));
  }
}
