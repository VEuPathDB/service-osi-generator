package org.veupathdb.service.osi.generated.resources;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import org.veupathdb.service.osi.generated.model.BadRequestError;
import org.veupathdb.service.osi.generated.model.CollectionResponse;
import org.veupathdb.service.osi.generated.model.ForbiddenError;
import org.veupathdb.service.osi.generated.model.IdSetCollectionPostRequest;
import org.veupathdb.service.osi.generated.model.NotFoundError;
import org.veupathdb.service.osi.generated.model.ServerError;
import org.veupathdb.service.osi.generated.model.SimpleCollectionResponse;
import org.veupathdb.service.osi.generated.model.UnauthorizedError;
import org.veupathdb.service.osi.generated.model.UnprocessableEntityError;
import org.veupathdb.service.osi.generated.support.ResponseDelegate;

@Path("/idSetCollections")
public interface IdSetCollections {
  @GET
  @Produces("application/json")
  GetIdSetCollectionsResponse getIdSetCollections(@QueryParam("createdAfter") Long createdAfter,
      @QueryParam("createdBefore") Long createdBefore, @QueryParam("createdBy") String createdBy,
      @QueryParam("name") String name);

  @POST
  @Produces("application/json")
  @Consumes("application/json")
  PostIdSetCollectionsResponse postIdSetCollections(IdSetCollectionPostRequest entity);

  @GET
  @Path("/{collection-id}")
  @Produces("application/json")
  GetIdSetCollectionsByCollectionIdResponse getIdSetCollectionsByCollectionId(
      @PathParam("collection-id") Long collectionId);

  class PostIdSetCollectionsResponse extends ResponseDelegate {
    private PostIdSetCollectionsResponse(Response response, Object entity) {
      super(response, entity);
    }

    private PostIdSetCollectionsResponse(Response response) {
      super(response);
    }

    public static PostIdSetCollectionsResponse respond200WithApplicationJson(
        CollectionResponse entity) {
      Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PostIdSetCollectionsResponse(responseBuilder.build(), entity);
    }

    public static PostIdSetCollectionsResponse respond400WithApplicationJson(
        BadRequestError entity) {
      Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PostIdSetCollectionsResponse(responseBuilder.build(), entity);
    }

    public static PostIdSetCollectionsResponse respond401WithApplicationJson(
        UnauthorizedError entity) {
      Response.ResponseBuilder responseBuilder = Response.status(401).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PostIdSetCollectionsResponse(responseBuilder.build(), entity);
    }

    public static PostIdSetCollectionsResponse respond403WithApplicationJson(
        ForbiddenError entity) {
      Response.ResponseBuilder responseBuilder = Response.status(403).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PostIdSetCollectionsResponse(responseBuilder.build(), entity);
    }

    public static PostIdSetCollectionsResponse respond422WithApplicationJson(
        UnprocessableEntityError entity) {
      Response.ResponseBuilder responseBuilder = Response.status(422).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PostIdSetCollectionsResponse(responseBuilder.build(), entity);
    }

    public static PostIdSetCollectionsResponse respond500WithApplicationJson(ServerError entity) {
      Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PostIdSetCollectionsResponse(responseBuilder.build(), entity);
    }
  }

  class GetIdSetCollectionsResponse extends ResponseDelegate {
    private GetIdSetCollectionsResponse(Response response, Object entity) {
      super(response, entity);
    }

    private GetIdSetCollectionsResponse(Response response) {
      super(response);
    }

    public static GetIdSetCollectionsResponse respond200WithApplicationJson(
        List<SimpleCollectionResponse> entity) {
      Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
      GenericEntity<List<SimpleCollectionResponse>> wrappedEntity = new GenericEntity<List<SimpleCollectionResponse>>(entity){};
      responseBuilder.entity(wrappedEntity);
      return new GetIdSetCollectionsResponse(responseBuilder.build(), wrappedEntity);
    }

    public static GetIdSetCollectionsResponse respond401WithApplicationJson(
        UnauthorizedError entity) {
      Response.ResponseBuilder responseBuilder = Response.status(401).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new GetIdSetCollectionsResponse(responseBuilder.build(), entity);
    }

    public static GetIdSetCollectionsResponse respond403WithApplicationJson(ForbiddenError entity) {
      Response.ResponseBuilder responseBuilder = Response.status(403).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new GetIdSetCollectionsResponse(responseBuilder.build(), entity);
    }

    public static GetIdSetCollectionsResponse respond500WithApplicationJson(ServerError entity) {
      Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new GetIdSetCollectionsResponse(responseBuilder.build(), entity);
    }
  }

  class GetIdSetCollectionsByCollectionIdResponse extends ResponseDelegate {
    private GetIdSetCollectionsByCollectionIdResponse(Response response, Object entity) {
      super(response, entity);
    }

    private GetIdSetCollectionsByCollectionIdResponse(Response response) {
      super(response);
    }

    public static GetIdSetCollectionsByCollectionIdResponse respond200WithApplicationJson(
        CollectionResponse entity) {
      Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new GetIdSetCollectionsByCollectionIdResponse(responseBuilder.build(), entity);
    }

    public static GetIdSetCollectionsByCollectionIdResponse respond401WithApplicationJson(
        UnauthorizedError entity) {
      Response.ResponseBuilder responseBuilder = Response.status(401).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new GetIdSetCollectionsByCollectionIdResponse(responseBuilder.build(), entity);
    }

    public static GetIdSetCollectionsByCollectionIdResponse respond404WithApplicationJson(
        NotFoundError entity) {
      Response.ResponseBuilder responseBuilder = Response.status(404).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new GetIdSetCollectionsByCollectionIdResponse(responseBuilder.build(), entity);
    }

    public static GetIdSetCollectionsByCollectionIdResponse respond500WithApplicationJson(
        ServerError entity) {
      Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new GetIdSetCollectionsByCollectionIdResponse(responseBuilder.build(), entity);
    }
  }
}
