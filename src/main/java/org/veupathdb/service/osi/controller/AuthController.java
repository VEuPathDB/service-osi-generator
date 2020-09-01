package org.veupathdb.service.osi.controller;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

import org.apache.logging.log4j.Logger;
import org.veupathdb.lib.container.jaxrs.providers.LogProvider;
import org.veupathdb.service.osi.generated.model.UserPostRequest;
import org.veupathdb.service.osi.generated.resources.Auth;
import org.veupathdb.service.osi.service.user.UserService;

public class AuthController implements Auth
{
  private final Logger log = LogProvider.logger(getClass());

  private final Request req;

  public AuthController(@Context Request req) {
    this.req = req;
  }

  @Override
  public PostAuthResponse postAuth(final UserPostRequest entity) {
    log.trace("AuthController#postAuth(UserPostRequest)");
    return PostAuthResponse.respond200WithApplicationJson(
      UserService.createUser(entity, req));
  }

  @Override
  public GetAuthByUserIdentifierResponse getAuthByUserIdentifier(
    final String userIdentifier
  ) {
    log.trace("AuthController#getAuthByUserIdentifier(String)");
    return GetAuthByUserIdentifierResponse.respond200WithApplicationJson(
      UserService.lookupUser(userIdentifier, req));
  }
}
