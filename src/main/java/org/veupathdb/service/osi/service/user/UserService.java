package org.veupathdb.service.osi.service.user;

import java.util.Objects;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.Request;

import org.glassfish.jersey.server.ContainerRequest;
import org.veupathdb.lib.container.jaxrs.Globals;
import org.veupathdb.service.osi.model.db.User;

public class UserService
{
  private static final UserService instance = new UserService();

  public static UserService getInstance() {
    return instance;
  }

  public static User createUser(AuthP)

  public static User requireRequestUser(Request req) {
    if (!(Objects.requireNonNull(req) instanceof ContainerRequest))
      throw new InternalServerErrorException("Invalid request type");
    var out = ((ContainerRequest) req).getProperty(Globals.REQUEST_USER);

    if (out == null)
      throw new NotAuthorizedException("Users must be logged in to use this endpoint");

    return (User) out;
  }
}
