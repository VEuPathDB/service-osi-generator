package org.veupathdb.service.osi.generated.resources;

import java.util.List;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.Response;
import org.veupathdb.service.osi.generated.model.BadRequestError;
import org.veupathdb.service.osi.generated.model.ForbiddenError;
import org.veupathdb.service.osi.generated.model.NotFoundError;
import org.veupathdb.service.osi.generated.model.OrganismPostRequest;
import org.veupathdb.service.osi.generated.model.OrganismPutRequest;
import org.veupathdb.service.osi.generated.model.OrganismResponse;
import org.veupathdb.service.osi.generated.model.ServerError;
import org.veupathdb.service.osi.generated.model.UnauthorizedError;
import org.veupathdb.service.osi.generated.model.UnprocessableEntityError;
import org.veupathdb.service.osi.generated.support.ResponseDelegate;

@Path("/organisms")
public interface Organisms {
  @GET
  @Produces("application/json")
  GetOrganismsResponse getOrganisms(@QueryParam("organismName") String organismName,
      @QueryParam("createdAfter") Long createdAfter,
      @QueryParam("createdBefore") Long createdBefore, @QueryParam("createdBy") String createdBy);

  @POST
  @Produces("application/json")
  @Consumes("application/json")
  PostOrganismsResponse postOrganisms(OrganismPostRequest entity);

  @GET
  @Path("/{organism-id}")
  @Produces("application/json")
  GetOrganismsByOrganismIdResponse getOrganismsByOrganismId(
      @PathParam("organism-id") String organismId);

  @PUT
  @Path("/{organism-id}")
  @Produces("application/json")
  @Consumes("application/json")
  PutOrganismsByOrganismIdResponse putOrganismsByOrganismId(
      @PathParam("organism-id") String organismId, OrganismPutRequest entity);

  class PostOrganismsResponse extends ResponseDelegate {
    private PostOrganismsResponse(Response response, Object entity) {
      super(response, entity);
    }

    private PostOrganismsResponse(Response response) {
      super(response);
    }

    public static PostOrganismsResponse respond200WithApplicationJson(Long entity) {
      Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PostOrganismsResponse(responseBuilder.build(), entity);
    }

    public static PostOrganismsResponse respond400WithApplicationJson(BadRequestError entity) {
      Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PostOrganismsResponse(responseBuilder.build(), entity);
    }

    public static PostOrganismsResponse respond401WithApplicationJson(UnauthorizedError entity) {
      Response.ResponseBuilder responseBuilder = Response.status(401).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PostOrganismsResponse(responseBuilder.build(), entity);
    }

    public static PostOrganismsResponse respond403WithApplicationJson(ForbiddenError entity) {
      Response.ResponseBuilder responseBuilder = Response.status(403).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PostOrganismsResponse(responseBuilder.build(), entity);
    }

    public static PostOrganismsResponse respond422WithApplicationJson(
        UnprocessableEntityError entity) {
      Response.ResponseBuilder responseBuilder = Response.status(422).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PostOrganismsResponse(responseBuilder.build(), entity);
    }

    public static PostOrganismsResponse respond500WithApplicationJson(ServerError entity) {
      Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PostOrganismsResponse(responseBuilder.build(), entity);
    }
  }

  class GetOrganismsResponse extends ResponseDelegate {
    private GetOrganismsResponse(Response response, Object entity) {
      super(response, entity);
    }

    private GetOrganismsResponse(Response response) {
      super(response);
    }

    public static GetOrganismsResponse respond200WithApplicationJson(
        List<OrganismResponse> entity) {
      Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
      GenericEntity<List<OrganismResponse>> wrappedEntity = new GenericEntity<List<OrganismResponse>>(entity){};
      responseBuilder.entity(wrappedEntity);
      return new GetOrganismsResponse(responseBuilder.build(), wrappedEntity);
    }

    public static GetOrganismsResponse respond401WithApplicationJson(UnauthorizedError entity) {
      Response.ResponseBuilder responseBuilder = Response.status(401).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new GetOrganismsResponse(responseBuilder.build(), entity);
    }

    public static GetOrganismsResponse respond403WithApplicationJson(ForbiddenError entity) {
      Response.ResponseBuilder responseBuilder = Response.status(403).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new GetOrganismsResponse(responseBuilder.build(), entity);
    }

    public static GetOrganismsResponse respond422WithApplicationJson(
        UnprocessableEntityError entity) {
      Response.ResponseBuilder responseBuilder = Response.status(422).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new GetOrganismsResponse(responseBuilder.build(), entity);
    }

    public static GetOrganismsResponse respond500WithApplicationJson(ServerError entity) {
      Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new GetOrganismsResponse(responseBuilder.build(), entity);
    }
  }

  class PutOrganismsByOrganismIdResponse extends ResponseDelegate {
    private PutOrganismsByOrganismIdResponse(Response response, Object entity) {
      super(response, entity);
    }

    private PutOrganismsByOrganismIdResponse(Response response) {
      super(response);
    }

    public static PutOrganismsByOrganismIdResponse respond204() {
      Response.ResponseBuilder responseBuilder = Response.status(204);
      return new PutOrganismsByOrganismIdResponse(responseBuilder.build());
    }

    public static PutOrganismsByOrganismIdResponse respond400WithApplicationJson(
        BadRequestError entity) {
      Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PutOrganismsByOrganismIdResponse(responseBuilder.build(), entity);
    }

    public static PutOrganismsByOrganismIdResponse respond401WithApplicationJson(
        UnauthorizedError entity) {
      Response.ResponseBuilder responseBuilder = Response.status(401).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PutOrganismsByOrganismIdResponse(responseBuilder.build(), entity);
    }

    public static PutOrganismsByOrganismIdResponse respond403WithApplicationJson(
        ForbiddenError entity) {
      Response.ResponseBuilder responseBuilder = Response.status(403).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PutOrganismsByOrganismIdResponse(responseBuilder.build(), entity);
    }

    public static PutOrganismsByOrganismIdResponse respond404WithApplicationJson(
        NotFoundError entity) {
      Response.ResponseBuilder responseBuilder = Response.status(404).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PutOrganismsByOrganismIdResponse(responseBuilder.build(), entity);
    }

    public static PutOrganismsByOrganismIdResponse respond422WithApplicationJson(
        UnprocessableEntityError entity) {
      Response.ResponseBuilder responseBuilder = Response.status(422).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PutOrganismsByOrganismIdResponse(responseBuilder.build(), entity);
    }

    public static PutOrganismsByOrganismIdResponse respond500WithApplicationJson(
        ServerError entity) {
      Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PutOrganismsByOrganismIdResponse(responseBuilder.build(), entity);
    }
  }

  class GetOrganismsByOrganismIdResponse extends ResponseDelegate {
    private GetOrganismsByOrganismIdResponse(Response response, Object entity) {
      super(response, entity);
    }

    private GetOrganismsByOrganismIdResponse(Response response) {
      super(response);
    }

    public static GetOrganismsByOrganismIdResponse respond200WithApplicationJson(
        OrganismResponse entity) {
      Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new GetOrganismsByOrganismIdResponse(responseBuilder.build(), entity);
    }

    public static GetOrganismsByOrganismIdResponse respond401WithApplicationJson(
        UnauthorizedError entity) {
      Response.ResponseBuilder responseBuilder = Response.status(401).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new GetOrganismsByOrganismIdResponse(responseBuilder.build(), entity);
    }

    public static GetOrganismsByOrganismIdResponse respond404WithApplicationJson(
        NotFoundError entity) {
      Response.ResponseBuilder responseBuilder = Response.status(404).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new GetOrganismsByOrganismIdResponse(responseBuilder.build(), entity);
    }

    public static GetOrganismsByOrganismIdResponse respond500WithApplicationJson(
        ServerError entity) {
      Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new GetOrganismsByOrganismIdResponse(responseBuilder.build(), entity);
    }
  }
}
