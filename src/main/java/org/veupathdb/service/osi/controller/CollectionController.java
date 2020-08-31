package org.veupathdb.service.osi.controller;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

import org.glassfish.jersey.server.ContainerRequest;
import org.veupathdb.service.osi.generated.model.IdSetCollectionPostRequest;
import org.veupathdb.service.osi.generated.resources.IdSetCollections;
import org.veupathdb.service.osi.service.collections.CollectionService;

public class CollectionController implements IdSetCollections
{
  private final ContainerRequest request;

  public CollectionController(@Context Request request) {
    this.request = (ContainerRequest) request;
  }

  @Override
  public GetIdSetCollectionsResponse getIdSetCollections(
    final Long createdAfter,
    final Long createdBefore,
    final String createdBy,
    final String name
  ) {
    return GetIdSetCollectionsResponse.respond200WithApplicationJson(
      CollectionService.search(
        name,
        createdAfter,
        createdBefore,
        createdBy,
        request));
  }

  @Override
  public PostIdSetCollectionsResponse postIdSetCollections(
    final IdSetCollectionPostRequest entity
  ) {
    return PostIdSetCollectionsResponse.respond200WithApplicationJson(
      CollectionService.create(entity, request));
  }

  @Override
  public GetIdSetCollectionsByCollectionIdResponse
  getIdSetCollectionsByCollectionId(final Long collectionId) {
    return GetIdSetCollectionsByCollectionIdResponse.
      respond200WithApplicationJson(
        CollectionService.lookup(collectionId, request));
  }
}
