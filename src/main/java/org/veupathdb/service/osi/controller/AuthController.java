package org.veupathdb.service.osi.controller;

import org.veupathdb.service.osi.generated.model.AuthPostApplicationJson;
import org.veupathdb.service.osi.generated.resources.Auth;

public class AuthController implements Auth
{
  @Override
  public PostAuthResponse postAuth(AuthPostApplicationJson entity) {
    return entity;
  }
}
