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
import org.veupathdb.service.osi.generated.model.ForbiddenError;
import org.veupathdb.service.osi.generated.model.GeneSet;
import org.veupathdb.service.osi.generated.model.IdentifiedGeneSet;
import org.veupathdb.service.osi.generated.model.NotFoundError;
import org.veupathdb.service.osi.generated.model.ServerError;
import org.veupathdb.service.osi.generated.model.UnauthorizedError;
import org.veupathdb.service.osi.generated.model.UnprocessableEntityError;
import org.veupathdb.service.osi.generated.support.ResponseDelegate;

@Path("/geneSets")
public interface GeneSets {
  @GET
  @Produces("application/json")
  GetGeneSetsResponse getGeneSets(@QueryParam("appName") String appName,
      @QueryParam("startTimeStamp") long startTimeStamp,
      @QueryParam("endTimeStamp") long endTimeStamp);

  @POST
  @Produces("application/json")
  @Consumes("application/json")
  PostGeneSetsResponse postGeneSets(GeneSet entity);

  @GET
  @Path("/{gene-set-id}")
  @Produces("application/json")
  GetGeneSetsByGeneSetIdResponse getGeneSetsByGeneSetId(@PathParam("gene-set-id") String geneSetId);

  class GetGeneSetsResponse extends ResponseDelegate {
    private GetGeneSetsResponse(Response response, Object entity) {
      super(response, entity);
    }

    private GetGeneSetsResponse(Response response) {
      super(response);
    }

    public static GetGeneSetsResponse respond200WithApplicationJson(
        List<IdentifiedGeneSet> entity) {
      Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
      GenericEntity<List<IdentifiedGeneSet>> wrappedEntity = new GenericEntity<List<IdentifiedGeneSet>>(entity){};
      responseBuilder.entity(wrappedEntity);
      return new GetGeneSetsResponse(responseBuilder.build(), wrappedEntity);
    }

    public static GetGeneSetsResponse respond401WithApplicationJson(UnauthorizedError entity) {
      Response.ResponseBuilder responseBuilder = Response.status(401).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new GetGeneSetsResponse(responseBuilder.build(), entity);
    }

    public static GetGeneSetsResponse respond403WithApplicationJson(ForbiddenError entity) {
      Response.ResponseBuilder responseBuilder = Response.status(403).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new GetGeneSetsResponse(responseBuilder.build(), entity);
    }

    public static GetGeneSetsResponse respond500WithApplicationJson(ServerError entity) {
      Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new GetGeneSetsResponse(responseBuilder.build(), entity);
    }
  }

  class PostGeneSetsResponse extends ResponseDelegate {
    private PostGeneSetsResponse(Response response, Object entity) {
      super(response, entity);
    }

    private PostGeneSetsResponse(Response response) {
      super(response);
    }

    public static PostGeneSetsResponse respond200WithApplicationJson(String entity) {
      Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PostGeneSetsResponse(responseBuilder.build(), entity);
    }

    public static PostGeneSetsResponse respond401WithApplicationJson(UnauthorizedError entity) {
      Response.ResponseBuilder responseBuilder = Response.status(401).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PostGeneSetsResponse(responseBuilder.build(), entity);
    }

    public static PostGeneSetsResponse respond403WithApplicationJson(ForbiddenError entity) {
      Response.ResponseBuilder responseBuilder = Response.status(403).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PostGeneSetsResponse(responseBuilder.build(), entity);
    }

    public static PostGeneSetsResponse respond422WithApplicationJson(
        UnprocessableEntityError entity) {
      Response.ResponseBuilder responseBuilder = Response.status(422).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PostGeneSetsResponse(responseBuilder.build(), entity);
    }

    public static PostGeneSetsResponse respond500WithApplicationJson(ServerError entity) {
      Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PostGeneSetsResponse(responseBuilder.build(), entity);
    }
  }

  class GetGeneSetsByGeneSetIdResponse extends ResponseDelegate {
    private GetGeneSetsByGeneSetIdResponse(Response response, Object entity) {
      super(response, entity);
    }

    private GetGeneSetsByGeneSetIdResponse(Response response) {
      super(response);
    }

    public static GetGeneSetsByGeneSetIdResponse respond200WithApplicationJson(
        IdentifiedGeneSet entity) {
      Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new GetGeneSetsByGeneSetIdResponse(responseBuilder.build(), entity);
    }

    public static GetGeneSetsByGeneSetIdResponse respond401WithApplicationJson(
        UnauthorizedError entity) {
      Response.ResponseBuilder responseBuilder = Response.status(401).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new GetGeneSetsByGeneSetIdResponse(responseBuilder.build(), entity);
    }

    public static GetGeneSetsByGeneSetIdResponse respond403WithApplicationJson(
        ForbiddenError entity) {
      Response.ResponseBuilder responseBuilder = Response.status(403).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new GetGeneSetsByGeneSetIdResponse(responseBuilder.build(), entity);
    }

    public static GetGeneSetsByGeneSetIdResponse respond404WithApplicationJson(
        NotFoundError entity) {
      Response.ResponseBuilder responseBuilder = Response.status(404).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new GetGeneSetsByGeneSetIdResponse(responseBuilder.build(), entity);
    }

    public static GetGeneSetsByGeneSetIdResponse respond500WithApplicationJson(ServerError entity) {
      Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new GetGeneSetsByGeneSetIdResponse(responseBuilder.build(), entity);
    }
  }
}
