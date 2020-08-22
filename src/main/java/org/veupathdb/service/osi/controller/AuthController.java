package org.veupathdb.service.osi.controller;

import java.util.*;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;

import org.veupathdb.lib.container.jaxrs.errors.UnprocessableEntityException;
import org.veupathdb.service.osi.generated.model.AuthPostApplicationJson;
import org.veupathdb.service.osi.generated.model.NewUserResponseImpl;
import org.veupathdb.service.osi.generated.resources.Auth;
import org.veupathdb.service.osi.service.UserManager;

public class AuthController implements Auth
{
  @Override
  public PostAuthResponse postAuth(AuthPostApplicationJson entity) {
    try {
      if (entity == null)
        throw new BadRequestException();

      if (entity.getUsername() == null || entity.getUsername().isBlank())
        throw new UnprocessableEntityException(new HashMap <String, List <String> >(){{
          put("username", new ArrayList <>(){{add("username is required");}});
        }});

      var user = UserManager.create(entity.getUsername());

      var out = new NewUserResponseImpl();
      out.setUserId(user.getUserId());
      out.setApiKey(user.getApiKey());
      out.setUserName(user.getUserName());
      out.setIssued(Date.from(user.getIssued().toInstant()));

      return PostAuthResponse.respond200WithApplicationJson(out);
    } catch (Exception e) {
      throw new InternalServerErrorException(e);
    }
  }
}
