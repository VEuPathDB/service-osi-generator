package org.veupathdb.service.osi.generated.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import org.veupathdb.service.osi.generated.model.*;
import org.veupathdb.service.osi.generated.support.ResponseDelegate;

@Path("/auth")
public interface Auth {
  @POST
  @Produces("application/json")
  @Consumes("application/json")
  PostAuthResponse postAuth(UserPostRequest entity);

  @GET
  @Path("/{user-identifier}")
  @Produces("application/json")
  GetAuthByUserIdentifierResponse getAuthByUserIdentifier(
      @PathParam("user-identifier") String userIdentifier);

  class PostAuthResponse extends ResponseDelegate {
    private PostAuthResponse(Response response, Object entity) {
      super(response, entity);
    }

    private PostAuthResponse(Response response) {
      super(response);
    }

    public static PostAuthResponse respond200WithApplicationJson(NewUserResponse entity) {
      Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PostAuthResponse(responseBuilder.build(), entity);
    }

    public static PostAuthResponse respond400WithApplicationJson(BadRequestError entity) {
      Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PostAuthResponse(responseBuilder.build(), entity);
    }

    public static PostAuthResponse respond401WithApplicationJson(UnauthorizedError entity) {
      Response.ResponseBuilder responseBuilder = Response.status(401).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PostAuthResponse(responseBuilder.build(), entity);
    }

    public static PostAuthResponse respond403WithApplicationJson(ForbiddenError entity) {
      Response.ResponseBuilder responseBuilder = Response.status(403).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PostAuthResponse(responseBuilder.build(), entity);
    }

    public static PostAuthResponse respond422WithApplicationJson(UnprocessableEntityError entity) {
      Response.ResponseBuilder responseBuilder = Response.status(422).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PostAuthResponse(responseBuilder.build(), entity);
    }

    public static PostAuthResponse respond500WithApplicationJson(ServerError entity) {
      Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PostAuthResponse(responseBuilder.build(), entity);
    }
  }

  class GetAuthByUserIdentifierResponse extends ResponseDelegate {
    private GetAuthByUserIdentifierResponse(Response response, Object entity) {
      super(response, entity);
    }

    private GetAuthByUserIdentifierResponse(Response response) {
      super(response);
    }

    public static GetAuthByUserIdentifierResponse respond200WithApplicationJson(
        NewUserResponse entity) {
      Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new GetAuthByUserIdentifierResponse(responseBuilder.build(), entity);
    }

    public static GetAuthByUserIdentifierResponse respond401WithApplicationJson(
        UnauthorizedError entity) {
      Response.ResponseBuilder responseBuilder = Response.status(401).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new GetAuthByUserIdentifierResponse(responseBuilder.build(), entity);
    }

    public static GetAuthByUserIdentifierResponse respond500WithApplicationJson(
        ServerError entity) {
      Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new GetAuthByUserIdentifierResponse(responseBuilder.build(), entity);
    }
  }
}
