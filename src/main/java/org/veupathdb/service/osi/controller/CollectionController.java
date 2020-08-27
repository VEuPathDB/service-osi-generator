package org.veupathdb.service.osi.controller;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

import org.glassfish.jersey.server.ContainerRequest;
import org.veupathdb.lib.container.jaxrs.Globals;
import org.veupathdb.lib.container.jaxrs.errors.UnprocessableEntityException;
import org.veupathdb.service.osi.generated.model.IdSetCollectionPostRequest;
import org.veupathdb.service.osi.generated.resources.IdSetCollections;
import org.veupathdb.service.osi.model.RecordQuery;
import org.veupathdb.service.osi.model.db.User;
import org.veupathdb.service.osi.service.collections.CollectionManager;

public class CollectionController implements IdSetCollections
{
  private final ContainerRequest request;

  public CollectionController(@Context Request request) {
    this.request = (ContainerRequest) request;
  }

  @Override
  public GetIdSetCollectionsResponse getIdSetCollections(
    Long createdAfter,
    Long createdBefore,
    String createdBy,
    String name
  ) {
    var query = new RecordQuery();

    if (createdAfter != null)
      query.setStart(OffsetDateTime.ofInstant(
        Instant.ofEpochSecond(createdAfter), ZoneId.systemDefault()));

    if (createdBefore != null)
      query.setEnd(OffsetDateTime.ofInstant(
        Instant.ofEpochSecond(createdBefore), ZoneId.systemDefault()));

    if (createdBy != null && !createdBy.isBlank())
      try {
        query.setCreatedById(Integer.parseInt(createdBy));
      } catch (NumberFormatException ignored) {
        query.setCreatedByName(createdBy);
      }

    if (name != null)
      query.setName(name);

    return GetIdSetCollectionsResponse.respond200WithApplicationJson(
      CollectionManager.findCollections(query));
  }

  @Override
  public PostIdSetCollectionsResponse postIdSetCollections(
    IdSetCollectionPostRequest entity
  ) {
    if (entity == null)
      throw new BadRequestException();

    if (entity.getName() == null || entity.getName().isBlank())
      throw new UnprocessableEntityException(new HashMap <String, List <String> >() {{
        put("name", new ArrayList <>() {{ add("collection name is required"); }});
      }});

    return PostIdSetCollectionsResponse.respond200WithApplicationJson(
      CollectionManager.createCollection(entity.getName(),
        (User) request.getProperty(Globals.REQUEST_USER)));
  }

  @Override
  public GetIdSetCollectionsByCollectionIdResponse getIdSetCollectionsByCollectionId(
    long collectionId
  ) {
    return GetIdSetCollectionsByCollectionIdResponse.
      respond200WithApplicationJson(CollectionManager.getCollection(collectionId));
  }
}
