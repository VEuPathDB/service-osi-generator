package org.veupathdb.service.osi.filter;

import java.util.Collections;
import java.util.Optional;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MultivaluedHashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.veupathdb.lib.container.jaxrs.Globals;
import org.veupathdb.service.osi.model.db.User;
import org.veupathdb.service.osi.service.user.UserManager;

import static org.junit.jupiter.api.Assertions.*;

class BasicAuthFilterTest
{
  private ContainerRequestContext ctx;
  private UserManager man;
  private User user;

  /**
   * Base64 encoded "test_username:test_password"
   */
  private String testAuthValue = "dGVzdF91c2VybmFtZTp0ZXN0X3Bhc3N3b3Jk";

  @BeforeEach
  void setCtx() throws Exception {
    ctx = Mockito.mock(ContainerRequestContext.class);
    man = Mockito.mock(UserManager.class);
    user = Mockito.mock(User.class);

    // Override internal instance with the mock instance
    final var field = UserManager.class.getField("instance");
    field.setAccessible(true);
    field.set(null, man);
  }

  @Test
  void filterValid() throws Exception {
    Mockito.when(ctx.getHeaders())
      .thenReturn(new MultivaluedHashMap <>(){{
        put("Authorization", Collections.singletonList("Basic " + testAuthValue));
      }});
    Mockito.when(man.lookupUser("test_username", "test_password"))
      .thenReturn(Optional.of(user));
    Mockito.verify(ctx).setProperty(Globals.REQUEST_USER, user);

    assertDoesNotThrow(() -> new BasicAuthFilter().filter(ctx));
  }
}
