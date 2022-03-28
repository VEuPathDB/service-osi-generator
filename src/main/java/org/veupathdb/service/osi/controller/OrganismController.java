package org.veupathdb.service.osi.controller;

import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Request;

import org.apache.logging.log4j.Logger;
import org.veupathdb.lib.container.jaxrs.providers.LogProvider;
import org.veupathdb.service.osi.Main;
import org.veupathdb.service.osi.generated.model.OrganismPostRequest;
import org.veupathdb.service.osi.generated.model.OrganismPutRequest;
import org.veupathdb.service.osi.generated.resources.Organisms;

import static org.veupathdb.service.osi.service.organism.OrganismService.*;

public class OrganismController implements Organisms
{
  private final Logger  log = LogProvider.logger(getClass());

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
    log.trace("OrganismController#getOrganisms(String, Long, Long, String)");
    return GetOrganismsResponse.respond200WithApplicationJson(
      search(organismName, createdAfter, createdBefore, createdBy, req));
  }

  @Override
  public PostOrganismsResponse postOrganisms(final OrganismPostRequest entity) {
    log.trace("OrganismController#postOrganisms(OrganismPostRequest)");

    if (entity == null) {
      throw new BadRequestException();
    }

    if (entity.getTranscriptIntStart() == null) {
      entity.setTranscriptIntStart(Main.config.getDefaultTranscriptStart());
    }

    return PostOrganismsResponse.respond200WithApplicationJson(
      create(entity, req));
  }

  @Override
  public GetOrganismsByOrganismIdResponse getOrganismsByOrganismId(
    final String organismId
  ) {
    log.trace("OrganismController#getOrganismsByOrganismId(String)");
    return GetOrganismsByOrganismIdResponse.respond200WithApplicationJson(
      get(organismId, req));
  }

  @Override
  public PutOrganismsByOrganismIdResponse putOrganismsByOrganismId(
    final String organismId,
    final OrganismPutRequest entity
  ) {
    log.trace("OrganismController#putOrganismsByOrganismId(String, OrganismPutRequest)");
    update(organismId, entity, req);
    return PutOrganismsByOrganismIdResponse.respond204();
  }
}
