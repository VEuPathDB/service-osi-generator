package org.veupathdb.service.osi.controller;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

import org.veupathdb.service.osi.generated.model.OrganismPostRequest;
import org.veupathdb.service.osi.generated.model.OrganismPutRequest;
import org.veupathdb.service.osi.generated.resources.Organisms;

import static org.veupathdb.service.osi.service.organism.OrganismService.*;

public class OrganismController implements Organisms
{
  private final Request req;

  public OrganismController(@Context Request req) {
    this.req = req;
  }

  @Override
  public GetOrganismsResponse getOrganisms(
    final String organismName,
    final Long   createdAfter,
    final Long   createdBefore,
    final String createdBy
  ) {
    return GetOrganismsResponse.respond200WithApplicationJson(
      search(organismName, createdAfter, createdBefore, createdBy, req));
  }

  @Override
  public PostOrganismsResponse postOrganisms(final OrganismPostRequest entity) {
    return PostOrganismsResponse.respond200WithApplicationJson(
      create(entity, req));
  }

  @Override
  public GetOrganismsByOrganismIdResponse getOrganismsByOrganismId(
    final String organismId
  ) {
    return GetOrganismsByOrganismIdResponse.respond200WithApplicationJson(
      get(organismId, req));
  }

  @Override
  public PutOrganismsByOrganismIdResponse putOrganismsByOrganismId(
    String organismId,
    OrganismPutRequest entity
  ) {
    update(organismId, entity, req);
    return PutOrganismsByOrganismIdResponse.respond204();
  }
}
