package org.veupathdb.service.osi.controller;

import java.util.List;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Request;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.veupathdb.service.osi.generated.model.IdSetPostRequest;
import org.veupathdb.service.osi.generated.model.IdSetResponse;
import org.veupathdb.service.osi.service.idset.IdSetService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IdSetControllerTest
{
  private static Request mRequest;

  private static IdSetService mService;

  @BeforeAll
  static void beforeAll() throws Exception {
    mRequest = mock(Request.class);
    mService = mock(IdSetService.class);

    var inst = IdSetService.class.getDeclaredField("instance");
    inst.setAccessible(true);
    inst.set(null, mService);
  }

  @Nested
  @DisplayName("IdSetController#getIdSets(Long, Long, String)")
  class GetIdSets
  {
    @Test
    @DisplayName("passes query parameters to the service layer")
    void happyPath() {
      var mResponse = mock(List.class);
      var after     = 123456L;
      var before    = 654321L;
      var user      = "some user";

      doReturn(mResponse).when(mService)
        .handleSearch(after, before, user, mRequest);

      var output = new IdSetController(mRequest)
        .getIdSets(after, before, user);

      assertNotNull(output);
      assertSame(mResponse, ((GenericEntity) output.getEntity()).getEntity());
      verify(mService).handleSearch(after, before, user, mRequest);
    }
  }

  @Nested
  @DisplayName("IdSetController#postIdSets(IdSetPostRequest)")
  class PostIdSets
  {
    @Test
    @DisplayName("passes post body to service layer")
    void happyPath() {
      var mResponse = mock(IdSetResponse.class);
      var body      = mock(IdSetPostRequest.class);

      doReturn(mResponse).when(mService).handleCreate(body, mRequest);

      var output = new IdSetController(mRequest).postIdSets(body);

      assertNotNull(output);
      assertSame(mResponse, output.getEntity());
      verify(mService).handleCreate(body, mRequest);
    }
  }

  @Nested
  @DisplayName("IdSetController#getIdSetsByIdSetId(Long)")
  class GetIdSetsByIdSetId
  {
    @Test
    @DisplayName("passes uri param to service layer")
    void happyPath() {
      var mResponse = mock(IdSetResponse.class);
      var uriParam  = 123456L;

      doReturn(mResponse).when(mService).handleGet(uriParam, mRequest);

      var output = new IdSetController(mRequest).getIdSetsByIdSetId(uriParam);

      assertNotNull(output);
      assertSame(mResponse, output.getEntity());
      verify(mService).handleGet(uriParam, mRequest);
    }
  }

  @Nested
  @DisplayName("IdSetController#patchIdSetsByIdSetId(Long, List)")
  class PatchIdSetsByIdSetId
  {
    @SuppressWarnings("unchecked")
    @Test
    @DisplayName("passes uri param and patch body to service layer")
    void happyPath() {
      var mResponse = mock(IdSetResponse.class);
      var uriParam  = 456789L;
      var body      = mock(List.class);

      doReturn(mResponse).when(mService).handleUpdate(uriParam, body, mRequest);

      var output = new IdSetController(mRequest)
        .patchIdSetsByIdSetId(uriParam, body);

      assertNotNull(output);
      assertSame(mResponse, output.getEntity());
      verify(mService).handleUpdate(uriParam, body, mRequest);
    }
  }
}
