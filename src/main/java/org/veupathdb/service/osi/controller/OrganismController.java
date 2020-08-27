package org.veupathdb.service.osi.controller;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

import org.veupathdb.service.osi.generated.model.OrganismPostRequest;
import org.veupathdb.service.osi.generated.model.OrganismPutRequest;
import org.veupathdb.service.osi.generated.resources.Organisms;
import org.veupathdb.service.osi.model.RecordQuery;
import org.veupathdb.service.osi.service.user.UserService;
import org.veupathdb.service.osi.util.Params;

import static org.veupathdb.service.osi.service.organism.OrganismService.*;

public class OrganismController implements Organisms
{
  private final Request req;

  public OrganismController(@Context Request req) {
    this.req = req;
  }

  @Override
  public GetOrganismsResponse getOrganisms(
    String organismName,
    Long   createdAfter,
    Long   createdBefore,
    String createdBy
  ) {
    UserService.requireRequestUser(req);

    var query = new RecordQuery()
      .setStart(Params.nullableTimestamp(createdAfter))
      .setEnd(Params.nullableTimestamp(createdBefore))
      .setName(organismName);

    if (createdBy != null)
      Params.stringOrLong(createdBy)
        .ifLeft(query::setCreatedByName)
        .ifRight(query::setCreatedById);

    return GetOrganismsResponse.respond200WithApplicationJson(handleSearch(query));
  }

  @Override
  public PostOrganismsResponse postOrganisms(OrganismPostRequest entity) {
    return PostOrganismsResponse.respond200WithApplicationJson(handleCreate(
      entity, UserService.requireRequestUser(req)));
  }

  @Override
  public GetOrganismsByOrganismIdResponse getOrganismsByOrganismId(String organismId) {
    UserService.requireRequestUser(req);
    return GetOrganismsByOrganismIdResponse.respond200WithApplicationJson(
      handleGet(organismId));
  }

  @Override
  public PutOrganismsByOrganismIdResponse putOrganismsByOrganismId(
    String organismId,
    OrganismPutRequest entity
  ) {
    UserService.requireRequestUser(req);
    handleUpdate(organismId, entity);
    return PutOrganismsByOrganismIdResponse.respond204();
  }
}
