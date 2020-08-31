package org.veupathdb.service.osi.controller;

import java.util.List;
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
      var target    = new IdSetController(mRequest);

      doReturn(mResponse).when(mService)
        .handleSearch(after, before, user, mRequest);

      assertNotNull(target.getIdSets(after, before, user));
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
      var target    = new IdSetController(mRequest);

      doReturn(mResponse).when(mService).handleCreate(body, mRequest);

      assertNotNull(target.postIdSets(body));
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
      var target    = new IdSetController(mRequest);

      doReturn(mResponse).when(mService).handleGet(uriParam, mRequest);

      assertNotNull(target.getIdSetsByIdSetId(uriParam));
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
      var target    = new IdSetController(mRequest);

      doReturn(mResponse).when(mService).handleUpdate(uriParam, body, mRequest);

      assertNotNull(target.patchIdSetsByIdSetId(uriParam, body));
      verify(mService).handleUpdate(uriParam, body, mRequest);
    }
  }
}
