package org.veupathdb.service.osi.controller;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

import org.veupathdb.service.osi.generated.model.UserPostRequest;
import org.veupathdb.service.osi.generated.resources.Auth;
import org.veupathdb.service.osi.service.user.UserService;

public class AuthController implements Auth
{
  private final Request req;

  public AuthController(@Context Request req) {
    this.req = req;
  }

  @Override
  public PostAuthResponse postAuth(UserPostRequest entity) {
    return PostAuthResponse.respond200WithApplicationJson(
      UserService.createUser(entity, req));
  }
}
