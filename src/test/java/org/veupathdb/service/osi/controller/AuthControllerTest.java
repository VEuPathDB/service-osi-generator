package org.veupathdb.service.osi.controller;

import java.util.UUID;
import javax.swing.text.html.parser.Entity;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Request;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.veupathdb.service.osi.generated.model.NewUserResponse;
import org.veupathdb.service.osi.generated.model.UserPostRequest;
import org.veupathdb.service.osi.service.user.UserService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthControllerTest
{
  private static Request mRequest;

  private static UserService mService;

  @BeforeAll
  static void beforeAll() throws Exception {
    mRequest = mock(Request.class);
    mService = mock(UserService.class);

    var inst = UserService.class.getDeclaredField("instance");
    inst.setAccessible(true);
    inst.set(null, mService);
  }

  @Nested
  @DisplayName("AuthController#postAuth(UserPostRequest)")
  class PostAuth
  {
    @Test
    @DisplayName("passes request body and request instance to service layer")
    void happyPath() {
      var mBody     = mock(UserPostRequest.class);
      var mResponse = mock(NewUserResponse.class);

      doReturn(mResponse).when(mService).createNewUser(mBody, mRequest);

      var output = new AuthController(mRequest).postAuth(mBody);

      assertNotNull(output);
      assertSame(mResponse, output.getEntity());
      verify(mService).createNewUser(mBody, mRequest);
    }
  }

  @Nested
  @DisplayName("AuthController#getAuthByUserIdentifier(String)")
  class GetAuthByUserIdentifier
  {
    @Test
    @DisplayName("passes uri param and request instance to service layer")
    void happyPath() {
      var uriParam  = UUID.randomUUID().toString();
      var mResponse = mock(NewUserResponse.class);

      doReturn(mResponse).when(mService).getUserRecord(uriParam, mRequest);

      var output = new AuthController(mRequest)
        .getAuthByUserIdentifier(uriParam);

      assertNotNull(output);
      assertSame(mResponse, output.getEntity());
      verify(mService).getUserRecord(uriParam, mRequest);
    }
  }
}
