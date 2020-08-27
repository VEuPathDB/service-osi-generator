package org.veupathdb.service.osi.service.user;

import java.util.*;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.Request;

import org.glassfish.jersey.server.ContainerRequest;
import org.veupathdb.lib.container.jaxrs.Globals;
import org.veupathdb.lib.container.jaxrs.errors.UnprocessableEntityException;
import org.veupathdb.service.osi.filter.BasicAuthFilter;
import org.veupathdb.service.osi.generated.model.NewUserResponse;
import org.veupathdb.service.osi.generated.model.UserPostRequest;
import org.veupathdb.service.osi.model.db.NewUser;
import org.veupathdb.service.osi.model.db.User;
import org.veupathdb.service.osi.util.Errors;

public class UserService
{
  private static final UserService instance = new UserService();

  public static UserService getInstance() {
    return instance;
  }

  /**
   * @see #createNewUser(UserPostRequest, Request)
   */
  public static NewUserResponse createUser(UserPostRequest body, Request req) {
    return getInstance().createNewUser(body, req);
  }

  public static User requireRequestUser(Request req) {
    if (!(Objects.requireNonNull(req) instanceof ContainerRequest))
      throw new InternalServerErrorException("Invalid request type");
    var out = ((ContainerRequest) req).getProperty(Globals.REQUEST_USER);

    if (out == null)
      throw new NotAuthorizedException("Users must be logged in to use this endpoint");

    return (User) out;
  }

  /**
   * Enforces the given request contained valid user admin credentials.
   *
   * @param req request to validate.
   *
   * @throws IllegalStateException if the given request is not a valid Grizzly
   * request object.
   * @throws ForbiddenException if the given request did not include valid user
   * admin credentials.
   */
  public void enforceAdminUser(final Request req) {
    if (!(req instanceof ContainerRequest))
      throw new IllegalStateException();
    var flag = ((ContainerRequest) req).getProperty(BasicAuthFilter.ADMIN_FLAG);
    if (!(flag instanceof Boolean) || !((Boolean) flag))
      throw new ForbiddenException();
  }

  /**
   * Attempts to create a new user record from the given request body.
   *
   * @param body POST request body.
   * @param req  Jersey request object.
   *
   * @return a response object to return to the client.
   *
   * @throws ForbiddenException if the client making the request did not include
   * valid user admin credentials.
   * @throws BadRequestException if the POST request body is null.
   * @throws UnprocessableEntityException if the {@code username} field in the
   * POST request body is null or blank.
   * @throws InternalServerErrorException if the new user insertion fails.
   *
   * @see #enforceAdminUser(Request)
   */
  public NewUserResponse createNewUser(UserPostRequest body, Request req) {
    enforceAdminUser(req);

    if (body == null)
      throw new BadRequestException();

    if (body.getUsername() == null || body.getUsername().isBlank())
      throw new UnprocessableEntityException(Collections.singletonMap(
        "username",
        Collections.singletonList("username is required")));

    try {
      return UserUtil.userToNewRes(UserRepo.insertNewUser(new NewUser(
        body.getUsername(),
        UUID.randomUUID().toString().replaceAll("-", ""))));
    } catch (Exception e) {
      throw Errors.wrapErr(e);
    }
  }
}
