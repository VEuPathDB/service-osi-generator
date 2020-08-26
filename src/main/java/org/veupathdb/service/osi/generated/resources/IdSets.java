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
import org.veupathdb.service.osi.generated.model.ForbiddenError;
import org.veupathdb.service.osi.generated.model.IdSetPatchEntry;
import org.veupathdb.service.osi.generated.model.IdSetPostRequest;
import org.veupathdb.service.osi.generated.model.IdSetResponse;
import org.veupathdb.service.osi.generated.model.NotFoundError;
import org.veupathdb.service.osi.generated.model.ServerError;
import org.veupathdb.service.osi.generated.model.UnauthorizedError;
import org.veupathdb.service.osi.generated.model.UnprocessableEntityError;
import org.veupathdb.service.osi.generated.support.PATCH;
import org.veupathdb.service.osi.generated.support.ResponseDelegate;

@Path("/idSets")
public interface IdSets {
  @GET
  @Produces("application/json")
  GetIdSetsResponse getIdSets(@QueryParam("createdAfter") long createdAfter,
      @QueryParam("createdBefore") long createdBefore, @QueryParam("createdBy") String createdBy);

  @POST
  @Produces("application/json")
  @Consumes("application/json")
  PostIdSetsResponse postIdSets(IdSetPostRequest entity);

  @GET
  @Path("/{gene-set-id}")
  @Produces("application/json")
  GetIdSetsByGeneSetIdResponse getIdSetsByGeneSetId(@PathParam("gene-set-id") String geneSetId);

  @PATCH
  @Path("/{gene-set-id}")
  @Produces("application/json")
  @Consumes("application/json")
  PatchIdSetsByGeneSetIdResponse patchIdSetsByGeneSetId(@PathParam("gene-set-id") String geneSetId,
      List<IdSetPatchEntry> entity);

  class PostIdSetsResponse extends ResponseDelegate {
    private PostIdSetsResponse(Response response, Object entity) {
      super(response, entity);
    }

    private PostIdSetsResponse(Response response) {
      super(response);
    }

    public static PostIdSetsResponse respond200WithApplicationJson(IdSetResponse entity) {
      Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PostIdSetsResponse(responseBuilder.build(), entity);
    }

    public static PostIdSetsResponse respond400WithApplicationJson(BadRequestError entity) {
      Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PostIdSetsResponse(responseBuilder.build(), entity);
    }

    public static PostIdSetsResponse respond401WithApplicationJson(UnauthorizedError entity) {
      Response.ResponseBuilder responseBuilder = Response.status(401).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PostIdSetsResponse(responseBuilder.build(), entity);
    }

    public static PostIdSetsResponse respond403WithApplicationJson(ForbiddenError entity) {
      Response.ResponseBuilder responseBuilder = Response.status(403).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PostIdSetsResponse(responseBuilder.build(), entity);
    }

    public static PostIdSetsResponse respond422WithApplicationJson(
        UnprocessableEntityError entity) {
      Response.ResponseBuilder responseBuilder = Response.status(422).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PostIdSetsResponse(responseBuilder.build(), entity);
    }

    public static PostIdSetsResponse respond500WithApplicationJson(ServerError entity) {
      Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PostIdSetsResponse(responseBuilder.build(), entity);
    }
  }

  class GetIdSetsResponse extends ResponseDelegate {
    private GetIdSetsResponse(Response response, Object entity) {
      super(response, entity);
    }

    private GetIdSetsResponse(Response response) {
      super(response);
    }

    public static GetIdSetsResponse respond200WithApplicationJson(List<IdSetResponse> entity) {
      Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
      GenericEntity<List<IdSetResponse>> wrappedEntity = new GenericEntity<List<IdSetResponse>>(entity){};
      responseBuilder.entity(wrappedEntity);
      return new GetIdSetsResponse(responseBuilder.build(), wrappedEntity);
    }

    public static GetIdSetsResponse respond401WithApplicationJson(UnauthorizedError entity) {
      Response.ResponseBuilder responseBuilder = Response.status(401).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new GetIdSetsResponse(responseBuilder.build(), entity);
    }

    public static GetIdSetsResponse respond403WithApplicationJson(ForbiddenError entity) {
      Response.ResponseBuilder responseBuilder = Response.status(403).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new GetIdSetsResponse(responseBuilder.build(), entity);
    }

    public static GetIdSetsResponse respond500WithApplicationJson(ServerError entity) {
      Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new GetIdSetsResponse(responseBuilder.build(), entity);
    }
  }

  class PatchIdSetsByGeneSetIdResponse extends ResponseDelegate {
    private PatchIdSetsByGeneSetIdResponse(Response response, Object entity) {
      super(response, entity);
    }

    private PatchIdSetsByGeneSetIdResponse(Response response) {
      super(response);
    }

    public static PatchIdSetsByGeneSetIdResponse respond200WithApplicationJson(
        IdSetResponse entity) {
      Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PatchIdSetsByGeneSetIdResponse(responseBuilder.build(), entity);
    }

    public static PatchIdSetsByGeneSetIdResponse respond400WithApplicationJson(
        BadRequestError entity) {
      Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PatchIdSetsByGeneSetIdResponse(responseBuilder.build(), entity);
    }

    public static PatchIdSetsByGeneSetIdResponse respond401WithApplicationJson(
        UnauthorizedError entity) {
      Response.ResponseBuilder responseBuilder = Response.status(401).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PatchIdSetsByGeneSetIdResponse(responseBuilder.build(), entity);
    }

    public static PatchIdSetsByGeneSetIdResponse respond403WithApplicationJson(
        ForbiddenError entity) {
      Response.ResponseBuilder responseBuilder = Response.status(403).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PatchIdSetsByGeneSetIdResponse(responseBuilder.build(), entity);
    }

    public static PatchIdSetsByGeneSetIdResponse respond404WithApplicationJson(
        NotFoundError entity) {
      Response.ResponseBuilder responseBuilder = Response.status(404).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PatchIdSetsByGeneSetIdResponse(responseBuilder.build(), entity);
    }

    public static PatchIdSetsByGeneSetIdResponse respond500WithApplicationJson(ServerError entity) {
      Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PatchIdSetsByGeneSetIdResponse(responseBuilder.build(), entity);
    }
  }

  class GetIdSetsByGeneSetIdResponse extends ResponseDelegate {
    private GetIdSetsByGeneSetIdResponse(Response response, Object entity) {
      super(response, entity);
    }

    private GetIdSetsByGeneSetIdResponse(Response response) {
      super(response);
    }

    public static GetIdSetsByGeneSetIdResponse respond200WithApplicationJson(IdSetResponse entity) {
      Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new GetIdSetsByGeneSetIdResponse(responseBuilder.build(), entity);
    }

    public static GetIdSetsByGeneSetIdResponse respond401WithApplicationJson(
        UnauthorizedError entity) {
      Response.ResponseBuilder responseBuilder = Response.status(401).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new GetIdSetsByGeneSetIdResponse(responseBuilder.build(), entity);
    }

    public static GetIdSetsByGeneSetIdResponse respond403WithApplicationJson(
        ForbiddenError entity) {
      Response.ResponseBuilder responseBuilder = Response.status(403).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new GetIdSetsByGeneSetIdResponse(responseBuilder.build(), entity);
    }

    public static GetIdSetsByGeneSetIdResponse respond404WithApplicationJson(NotFoundError entity) {
      Response.ResponseBuilder responseBuilder = Response.status(404).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new GetIdSetsByGeneSetIdResponse(responseBuilder.build(), entity);
    }

    public static GetIdSetsByGeneSetIdResponse respond500WithApplicationJson(ServerError entity) {
      Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new GetIdSetsByGeneSetIdResponse(responseBuilder.build(), entity);
    }
  }
}
