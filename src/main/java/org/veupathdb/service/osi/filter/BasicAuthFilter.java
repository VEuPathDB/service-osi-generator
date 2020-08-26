package org.veupathdb.service.osi.filter;

import java.io.IOException;
import java.util.Base64;
import javax.annotation.Priority;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

import org.veupathdb.lib.container.jaxrs.Globals;
import org.veupathdb.service.osi.service.user.UserManager;

@Provider
@Priority(4)
public class BasicAuthFilter implements ContainerRequestFilter
{
  private static final String
    authHeader = "Authorization",
    authPrefix = "Basic ";

  private final String user;
  private final String pass;

  public BasicAuthFilter(String user, String pass) {
    this.user = user;
    this.pass = pass;
  }

  @Override
  public void filter(ContainerRequestContext ctx) throws IOException {
    final var headers = ctx.getHeaders();

    if (!headers.containsKey(authHeader))
      throw new NotAuthorizedException("Missing required auth header.");

    final var auth = headers.getFirst(authHeader);

    if (!auth.startsWith(authPrefix))
      throw new BadRequestException("Invalid auth header format.");

    try {
      final var joined = new String(Base64.getDecoder()
        .decode(auth.substring(authPrefix.length())));
      final var split = joined.split(":", 1);

      // For the specific case of a POST request to the /auth endpoint with the
      // admin credentials let the request through without a user lookup.
      if (split[0].equals(user)
        && split[1].equals(pass)
        && ctx.getUriInfo().getPath().equals("/auth")
        && ctx.getMethod().equals("POST")
      )
        return;

      final var opt = UserManager.lookup(split[0], split[1]);

      if (opt.isEmpty())
        throw new NotAuthorizedException("No such user");

      ctx.setProperty(Globals.REQUEST_USER, opt.get());
    } catch (WebApplicationException e) {
      throw e;
    } catch (Exception e) {
      throw new InternalServerErrorException(e);
    }
  }
}
