package org.veupathdb.service.osi.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import javax.annotation.Priority;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

import org.apache.logging.log4j.Logger;
import org.veupathdb.lib.container.jaxrs.Globals;
import org.veupathdb.lib.container.jaxrs.providers.LogProvider;
import org.veupathdb.service.osi.service.user.UserManager;
import org.veupathdb.service.osi.util.InputValidationException;
import org.veupathdb.service.osi.util.Validation;

@Provider
@Priority(4)
public class BasicAuthFilter implements ContainerRequestFilter
{
  /**
   * Minimum number of characters an admin username must contain.
   */
  private static final int MIN_USER_LENGTH = 8;

  /**
   * Minimum number of characters an admin password must contain.
   */
  private static final int MIN_PASS_LENGTH = 32;

  public static final String ADMIN_FLAG = "userIsAdmin";

  public static final String ADMIN_PATH = "auth";

  private static final String
    authHeader = "Authorization",
    authPrefix = "Basic ";

  private static final String[] excludedPaths = {
    "health",
    "metrics",
    "api"
  };

  private final Logger log = LogProvider.logger(getClass());

  private final String user;
  private final String pass;

  /**
   * Creates a new instance of the {@code BasicAuthFilter} class with the given
   * user admin credentials.
   * <p>
   * The user admin credentials are to support an auth exception for the
   * {@code /auth} endpoints.  Those endpoints are only accessible to requests
   * made with these admin credentials.  Additionally, these credentials grant
   * no access to any other services or endpoints.
   * <p>
   * To enforce some level of security on these credentials, the {@code pass}
   * value must be at least {@link #MIN_PASS_LENGTH} characters in length.
   *
   * @param user User creation admin credentials username.
   * @param pass User creation admin credentials password.
   *
   * @throws InputValidationException if any of the following are true:
   * <ul>
   *   <li>{@code user} is {@code null}</li>
   *   <li>{@code user} contains only space characters</li>
   *   <li>{@code user.length()} is less than {@link #MIN_USER_LENGTH} characters</li>
   *   <li>{@code pass} is {@code null}</li>
   *   <li>{@code pass} contains only space characters</li>
   *   <li>{@code pass.length()} is less than {@link #MIN_PASS_LENGTH} characters</li>
   * </ul>
   */
  public BasicAuthFilter(String user, String pass) {
    this.user = Validation.minLength(MIN_USER_LENGTH, user);
    this.pass = Validation.minLength(MIN_PASS_LENGTH, pass);
  }

  @Override
  public void filter(ContainerRequestContext ctx) throws IOException {
    log.trace("BasicAuthFilter#filter(ContainerRequestContext)");
    final var headers = ctx.getHeaders();
    final var path    = ctx.getUriInfo().getPath();

    if (Arrays.asList(excludedPaths).contains(path))
      return;

    if (!headers.containsKey(authHeader))
      throw new NotAuthorizedException("Missing required auth header.");

    final var auth = headers.getFirst(authHeader);

    if (!auth.startsWith(authPrefix))
      throw new BadRequestException("Invalid auth header format.");

    try {
      final var joined = new String(Base64.getDecoder()
        .decode(auth.substring(authPrefix.length())));
      final var split = joined.split(":", 2);

      // For the specific case of a POST request to the /auth endpoint with the
      // admin credentials let the request through without a user lookup.
      if (split[0].equals(user)
        && split[1].equals(pass)
        && path.split("/", 2)[0].equals(ADMIN_PATH)
      ) {
        ctx.setProperty(ADMIN_FLAG, true);
        return;
      }

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
