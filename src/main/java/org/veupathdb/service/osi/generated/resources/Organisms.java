package org.veupathdb.service.osi.generated.resources;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import org.veupathdb.service.osi.generated.model.BadRequestError;
import org.veupathdb.service.osi.generated.model.ForbiddenError;
import org.veupathdb.service.osi.generated.model.NotFoundError;
import org.veupathdb.service.osi.generated.model.OrganismRequest;
import org.veupathdb.service.osi.generated.model.OrganismResponse;
import org.veupathdb.service.osi.generated.model.ServerError;
import org.veupathdb.service.osi.generated.model.UnauthorizedError;
import org.veupathdb.service.osi.generated.model.UnprocessableEntityError;
import org.veupathdb.service.osi.generated.support.ResponseDelegate;

@Path("/organisms")
public interface Organisms {
  @GET
  @Produces("application/json")
  GetOrganismsResponse getOrganisms(@QueryParam("organismName") String organismName);

  @POST
  @Produces("application/json")
  @Consumes("application/json")
  PostOrganismsResponse postOrganisms(OrganismRequest entity);

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
      @PathParam("organism-id") String organismId, OrganismRequest entity);

  class PostOrganismsResponse extends ResponseDelegate {
    private PostOrganismsResponse(Response response, Object entity) {
      super(response, entity);
    }

    private PostOrganismsResponse(Response response) {
      super(response);
    }

    public static PostOrganismsResponse respond200WithApplicationJson(String entity) {
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
