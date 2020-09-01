package org.veupathdb.service.osi.controller;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

import org.apache.logging.log4j.Logger;
import org.veupathdb.lib.container.jaxrs.providers.LogProvider;
import org.veupathdb.service.osi.generated.model.IdSetCollectionPostRequest;
import org.veupathdb.service.osi.generated.resources.IdSetCollections;
import org.veupathdb.service.osi.service.collections.CollectionService;

public class CollectionController implements IdSetCollections
{
  private final Logger  log = LogProvider.logger(getClass());

  private final Request request;

  public CollectionController(@Context Request request) {
    this.request = request;
  }

  @Override
  public GetIdSetCollectionsResponse getIdSetCollections(
    final Long createdAfter,
    final Long createdBefore,
    final String createdBy,
    final String name
  ) {
    log.trace("CollectionController#getIdSetCollections(Long, Long, String, String)");
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
    log.trace("CollectionController#postIdSetCollections(IdSetCollectionPostRequest)");
    return PostIdSetCollectionsResponse.respond200WithApplicationJson(
      CollectionService.create(entity, request));
  }

  @Override
  public GetIdSetCollectionsByCollectionIdResponse
  getIdSetCollectionsByCollectionId(final Long collectionId) {
    log.trace("CollectionController#getIdSetCollectionsByCollectionId(Long)");
    return GetIdSetCollectionsByCollectionIdResponse.
      respond200WithApplicationJson(
        CollectionService.lookup(collectionId, request));
  }
}
